package joaquim.jwttranslation.dto

import io.swagger.v3.oas.annotations.media.Schema

class UsuarioLoginDto {
    @Schema(description = "E-mail do usuário", example = "john@doe.com")
    var email: String? = null

    @Schema(description = "Senha do usuário", example = "123456")
    var senha: String? = null
}