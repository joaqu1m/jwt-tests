package joaquim.jwttranslation.security

import joaquim.jwttranslation.dto.UsuarioDetalhesDto
import joaquim.jwttranslation.model.Usuario
import joaquim.jwttranslation.repository.UsuarioRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service
import java.util.*

@Service
class AutenticacaoService : UserDetailsService {
    @Autowired
    private val usuarioRepository: UsuarioRepository? = null

    // Método da interface implementada
    @Throws(UsernameNotFoundException::class)
    override fun loadUserByUsername(username: String): UserDetails {
        val usuarioOpt: Optional<Usuario> = usuarioRepository!!.findByEmail(username)
        if (usuarioOpt.isEmpty) {
            throw UsernameNotFoundException(String.format("usuario: %s nao encontrado", username))
        }
        return UsuarioDetalhesDto(usuarioOpt.get())
    }
}