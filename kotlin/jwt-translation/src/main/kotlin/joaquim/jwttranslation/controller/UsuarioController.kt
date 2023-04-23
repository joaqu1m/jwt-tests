package joaquim.jwttranslation.controller

import io.swagger.v3.oas.annotations.security.SecurityRequirement
import joaquim.jwttranslation.dto.UsuarioLoginDto
import joaquim.jwttranslation.model.Usuario
import joaquim.jwttranslation.repository.UsuarioRepository
import joaquim.jwttranslation.security.JwtTokenManager
import org.springframework.http.ResponseEntity
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.web.bind.annotation.*
import java.util.Optional

@RestController
@RequestMapping("/usuarios")
class UsuarioController (
    val passwordEncoder: PasswordEncoder,
    val usuarioRepository: UsuarioRepository,
    val jwtTokenManager: JwtTokenManager,
    val authenticationManager: AuthenticationManager
) {

    @PostMapping("/cadastro")
    fun criar(@RequestBody usuario: Usuario): ResponseEntity<Void> {
        usuario.senha = passwordEncoder.encode(usuario.senha.toString())
        usuarioRepository.save(usuario)
        return ResponseEntity.status(201).build()
    }

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

    @PostMapping("/testando/sem-token")
    fun postTestandoSemToken(): ResponseEntity<Void> {
        return ResponseEntity.status(200).build()
    }

    @GetMapping("/testando/sem-token")
    fun getTestandoSemToken(): ResponseEntity<Void> {
        return ResponseEntity.status(200).build()
    }

    @SecurityRequirement(name = "Bearer")
    @PostMapping("/testando/com-token")
    fun postTestandoComToken(): ResponseEntity<Void> {
        return ResponseEntity.status(200).build()
    }

    @SecurityRequirement(name = "Bearer")
    @GetMapping("/testando/com-token")
    fun getTestandoComToken(): ResponseEntity<Void> {
        return ResponseEntity.status(200).build()
    }
}