package manuall.newproject.dto

import io.swagger.v3.oas.annotations.media.Schema

class UsuarioLoginDto {

    @Schema(description = "E-mail do usuário", example = "joaquim.pires@sptech.school")
    var email: String? = null

    @Schema(description = "Senha do usuário", example = "urubu100")
    var senha: String? = null
}