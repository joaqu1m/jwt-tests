package manuall.newproject.dto

import manuall.newproject.model.*

data class CadastroRequest (
    val areaUsuario: AreaUsuario,
    val dadosBancarios: DadosBancarios,
    val dadosEndereco: DadosEndereco,
    val descServicos: DescServicos,
    val usuario: Usuario
)