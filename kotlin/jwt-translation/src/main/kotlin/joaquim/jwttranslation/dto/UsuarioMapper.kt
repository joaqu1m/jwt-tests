package joaquim.jwttranslation.dto

import joaquim.jwttranslation.model.Usuario

object UsuarioMapper {
    fun of(usuarioCriacaoDto: UsuarioCriacaoDto): Usuario {
        val usuario = Usuario()
        usuario.email = usuarioCriacaoDto.email
        usuario.nome = usuarioCriacaoDto.nome
        usuario.senha = usuarioCriacaoDto.senha
        return usuario
    }

    fun of(usuario: Usuario, token: String?): UsuarioTokenDto {
        val usuarioTokenDto = UsuarioTokenDto()
        usuarioTokenDto.userId = usuario.id
        usuarioTokenDto.email = usuario.email
        usuarioTokenDto.nome = usuario.nome
        usuarioTokenDto.token = token
        return usuarioTokenDto
    }
}