package joaquim.jwttranslation.repository

import joaquim.jwttranslation.model.Usuario
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface UsuarioRepository : JpaRepository<Usuario, Long?> {
    fun findByEmail(email: String?): Optional<Usuario>
}
