package manuall.newproject.security

import manuall.newproject.model.Usuario
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.userdetails.UserDetails

class UserDetailsDto (
    private val usuario: Usuario
): UserDetails {

    override fun getAuthorities(): Collection<GrantedAuthority>? {
        return null
    }

    override fun getPassword(): String? {
        return usuario.senha
    }

    override fun getUsername(): String? {
        return usuario.email
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