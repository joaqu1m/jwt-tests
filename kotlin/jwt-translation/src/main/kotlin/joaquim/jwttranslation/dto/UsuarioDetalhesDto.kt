package joaquim.jwttranslation.dto

import joaquim.jwttranslation.model.Usuario
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.userdetails.UserDetails

class UsuarioDetalhesDto(usuario: Usuario) : UserDetails {
    val nome: String
    private val email: String
    private val senha: String

    init {
        nome = usuario.nome.toString()
        email = usuario.email.toString()
        senha = usuario.senha.toString()
    }

    override fun getAuthorities(): Collection<GrantedAuthority>? {
        return null
    }

    override fun getPassword(): String {
        return senha
    }

    override fun getUsername(): String {
        return email
    }

    override fun isAccountNonExpired(): Boolean {
        return true
    }

    override fun isAccountNonLocked(): Boolean {
        return true
    }

    override fun isCredentialsNonExpired(): Boolean {
        return true
    }

    override fun isEnabled(): Boolean {
        return true
    }
}