package manuall.newproject.security

import manuall.newproject.model.Usuario
import manuall.newproject.repository.UsuarioRepository
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service
import java.util.*

@Service
class JwtAuthenticationService (
    val usuarioRepository: UsuarioRepository
): UserDetailsService {

    @Throws(UsernameNotFoundException::class)
    override fun loadUserByUsername(username: String): UserDetails {
        val usuarioOpt: Optional<Usuario> = usuarioRepository.findByEmail(username)
        if (usuarioOpt.isEmpty) {
            throw UsernameNotFoundException(String.format("usuario: %s nao encontrado", username))
        }
        return UserDetailsDto(usuarioOpt.get())
    }
}