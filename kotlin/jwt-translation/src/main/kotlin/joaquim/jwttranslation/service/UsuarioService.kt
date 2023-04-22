package joaquim.jwttranslation.service

import joaquim.jwttranslation.dto.UsuarioCriacaoDto
import joaquim.jwttranslation.dto.UsuarioLoginDto
import joaquim.jwttranslation.dto.UsuarioMapper
import joaquim.jwttranslation.dto.UsuarioTokenDto
import joaquim.jwttranslation.model.Usuario
import joaquim.jwttranslation.repository.UsuarioRepository
import joaquim.jwttranslation.security.GerenciadorTokenJwt
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.web.server.ResponseStatusException

@Service
class UsuarioService {
    @Autowired
    private val passwordEncoder: PasswordEncoder? = null

    @Autowired
    private val usuarioRepository: UsuarioRepository? = null

    @Autowired
    private val gerenciadorTokenJwt: GerenciadorTokenJwt? = null

    @Autowired
    private val authenticationManager: AuthenticationManager? = null
    fun criar(usuarioCriacaoDto: UsuarioCriacaoDto) {
        val novoUsuario: Usuario = UsuarioMapper.of(usuarioCriacaoDto)
        val senhaCriptografada = passwordEncoder!!.encode(novoUsuario.senha.toString())
        novoUsuario.senha = senhaCriptografada
        usuarioRepository!!.save(novoUsuario)
    }

    fun autenticar(usuarioLoginDto: UsuarioLoginDto): UsuarioTokenDto {
        val credentials = UsernamePasswordAuthenticationToken(
            usuarioLoginDto.email, usuarioLoginDto.senha
        )
        val authentication = authenticationManager!!.authenticate(credentials)
        val usuarioAutenticado: Usuario = usuarioRepository!!.findByEmail(usuarioLoginDto.email)
            .orElseThrow { ResponseStatusException(404, "Email do usuário não cadastrado", null) }
        SecurityContextHolder.getContext().authentication = authentication
        val token: String = gerenciadorTokenJwt!!.generateToken(authentication)
        return UsuarioMapper.of(usuarioAutenticado, token)
    }
}