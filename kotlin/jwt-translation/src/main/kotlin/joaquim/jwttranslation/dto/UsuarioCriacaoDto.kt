package joaquim.jwttranslation.dto

import jakarta.validation.constraints.Email
import jakarta.validation.constraints.Size

class UsuarioCriacaoDto {
    var nome: @Size(min = 3, max = 10) String? = null
    var email: @Email String? = null
    var senha: @Size(min = 6, max = 20) String? = null
}