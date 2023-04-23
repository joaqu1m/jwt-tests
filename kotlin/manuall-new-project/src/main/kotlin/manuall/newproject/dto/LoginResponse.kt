package manuall.newproject.dto

import manuall.newproject.model.Usuario

data class LoginResponse (
    val qntPerfis: Int,
    val usuario: List<Usuario>?
)