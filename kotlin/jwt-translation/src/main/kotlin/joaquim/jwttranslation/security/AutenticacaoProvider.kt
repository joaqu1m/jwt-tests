package joaquim.jwttranslation.security

import org.springframework.security.authentication.AuthenticationProvider
import org.springframework.security.authentication.BadCredentialsException
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.AuthenticationException
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.crypto.password.PasswordEncoder

class AutenticacaoProvider(usuarioAutorizacaoService: AutenticacaoService?, passwordEncoder: PasswordEncoder) :
    AuthenticationProvider {
    private val usuarioAutorizacaoService: AutenticacaoService?
    private val passwordEncoder: PasswordEncoder

    init {
        this.usuarioAutorizacaoService = usuarioAutorizacaoService
        this.passwordEncoder = passwordEncoder
    }

    @Throws(AuthenticationException::class)
    override fun authenticate(authentication: Authentication): Authentication {
        val username = authentication.name
        val password = authentication.credentials.toString()
        val userDetails: UserDetails = usuarioAutorizacaoService!!.loadUserByUsername(username)
        return if (passwordEncoder.matches(password, userDetails.password)) {
            UsernamePasswordAuthenticationToken(userDetails, null, userDetails.authorities)
        } else {
            throw BadCredentialsException("Usuário ou Senha inválidos")
        }
    }

    override fun supports(authentication: Class<*>): Boolean {
        return authentication == UsernamePasswordAuthenticationToken::class.java
    }
}