package manuall.newproject.controller

import io.swagger.v3.oas.annotations.security.SecurityRequirement
import jakarta.transaction.Transactional
import manuall.newproject.dto.AlterDescRequest
import manuall.newproject.dto.AlterSenhaRequest
import manuall.newproject.dto.CadastroRequest
import manuall.newproject.dto.UsuarioLoginDto
import manuall.newproject.model.Usuario
import manuall.newproject.repository.*
import manuall.newproject.security.JwtTokenManager
import org.springframework.http.ResponseEntity
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.web.bind.annotation.*
import java.util.*

@RestController
@RequestMapping("/usuarios")
class UsuarioController (
    val passwordEncoder: PasswordEncoder,
    val jwtTokenManager: JwtTokenManager,
    val authenticationManager: AuthenticationManager,
    val usuarioRepository: UsuarioRepository,
    val dadosBancariosRepository: DadosBancariosRepository,
    val dadosEnderecoRepository: DadosEnderecoRepository,
    val areaUsuarioRepository: AreaUsuarioRepository,
    val descServicosRepository: DescServicosRepository
) {

    @PostMapping("/login")
    fun login(@RequestBody usuarioLoginDto: UsuarioLoginDto): ResponseEntity<String> {

        return if (usuarioRepository.findByEmail(usuarioLoginDto.email).isEmpty) {
            ResponseEntity.status(204).build()
        } else {

            val authentication = authenticationManager.authenticate(
                UsernamePasswordAuthenticationToken(
                    usuarioLoginDto.email,
                    usuarioLoginDto.senha
                )
            )

            SecurityContextHolder.getContext().authentication = authentication
            ResponseEntity.status(200).body(jwtTokenManager.generateToken(authentication))

        }

    }

    @PostMapping("/cadastrar")
    fun criar(@RequestBody cadastroRequest: CadastroRequest): ResponseEntity<Void> {

        val usuarioCheck: Usuario? = usuarioRepository.findByEmailAndTipoUsuario(cadastroRequest.usuario.email, cadastroRequest.usuario.tipoUsuario)

        return if (usuarioCheck == null) {

            cadastroRequest.usuario.senha = passwordEncoder.encode(cadastroRequest.usuario.senha.toString())
            val usuarioAtual: Int = usuarioRepository.save(cadastroRequest.usuario).id
            cadastroRequest.areaUsuario.usuario.id = usuarioAtual
            cadastroRequest.dadosBancarios.usuario.id = usuarioAtual
            cadastroRequest.dadosEndereco.usuario.id = usuarioAtual
            cadastroRequest.descServicos.usuario.id = usuarioAtual
            areaUsuarioRepository.save(cadastroRequest.areaUsuario)
            dadosBancariosRepository.save(cadastroRequest.dadosBancarios)
            dadosEnderecoRepository.save(cadastroRequest.dadosEndereco)
            descServicosRepository.save(cadastroRequest.descServicos)

            ResponseEntity.status(201).build()
        } else {
            ResponseEntity.status(409).build()
        }
    }

    @SecurityRequirement(name = "Bearer")
    @PatchMapping("/alterar/senha")
    fun atualizarSenha(@RequestHeader("Authorization") token: String, @RequestBody alterSenhaRequest: AlterSenhaRequest): ResponseEntity<Usuario?> {

        val usuarioEncontrado = usuarioRepository.findByEmail(jwtTokenManager.getUsernameFromToken(token.substring(7)))

        return if (alterSenhaRequest.senhaAntiga == usuarioEncontrado.get().senha) {

            val usuario: Usuario = usuarioEncontrado.get()
            usuario.senha = alterSenhaRequest.senhaNova
            ResponseEntity.status(200).body(usuarioRepository.save(usuario))

        } else {
            ResponseEntity.status(401).build()
        }
    }

    @SecurityRequirement(name = "Bearer")
    @PatchMapping("/alterar/desc")
    fun atualizarDesc(@RequestHeader("Authorization") token: String, @RequestBody alterDescRequest: AlterDescRequest): ResponseEntity<Usuario?> {

        val usuarioEncontrado = usuarioRepository.findByEmail(jwtTokenManager.getUsernameFromToken(token.substring(7)))

        val usuario: Usuario = usuarioEncontrado.get()

        usuario.descricao = alterDescRequest.descricao
        return ResponseEntity.status(200).body(usuarioRepository.save(usuario))
    }

    @Transactional
    @SecurityRequirement(name = "Bearer")
    @DeleteMapping("/deletar")
    fun deletar(@RequestHeader("Authorization") token: String): ResponseEntity<Void> {

        val usuarioEncontrado = usuarioRepository.findByEmail(jwtTokenManager.getUsernameFromToken(token.substring(7)))

        descServicosRepository.deleteByUsuarioId(usuarioEncontrado.get().id)
        dadosBancariosRepository.deleteByUsuarioId(usuarioEncontrado.get().id)
        areaUsuarioRepository.deleteByUsuarioId(usuarioEncontrado.get().id)
        dadosEnderecoRepository.deleteByUsuarioId(usuarioEncontrado.get().id)
        usuarioRepository.deleteById(usuarioEncontrado.get().id)
        return ResponseEntity.status(200).body(null)
    }


    @GetMapping("/checar/{email}")
    fun findByEmail(@PathVariable email: String): Optional<Usuario> {
        return usuarioRepository.findByEmail(email)
    }

    @GetMapping("/checar/{email}/{tipoUsuario}")
    fun findByEmailAndTipoUsuario(@PathVariable email: String, @PathVariable tipoUsuario: Int): Usuario? {
        return usuarioRepository.findByEmailAndTipoUsuario(email, tipoUsuario)
    }

    @GetMapping("/example/sem-token")
    fun getTestandoSemToken(): ResponseEntity<Void> {
        return ResponseEntity.status(200).build()
    }

    @SecurityRequirement(name = "Bearer")
    @GetMapping("/example/com-token")
    fun getTestandoComToken(@RequestHeader("Authorization") token: String): ResponseEntity<String> {
        return ResponseEntity.status(200).body(jwtTokenManager.getUsernameFromToken(token.substring(7)))
    }
}