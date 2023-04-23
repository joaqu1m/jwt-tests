package manuall.newproject.model

import jakarta.persistence.*

@Entity
@Table(name = "usuario")
class Usuario {

    @field:Id
    @field:GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    var id: Int = 0

    @Column(name = "nome", length = 60)
    var nome: String? = null

    @Column(name = "email", length = 256)
    var email: String? = null

    @Column(name = "senha", length = 60)
    var senha: String? = null

    @Column(name = "cpf", length = 11)
    var cpf: String? = null

    @Column(name = "orcamento_min")
    var orcamentoMin: Double? = null

    @Column(name = "orcamento_max")
    var orcamentoMax: Double? = null

    @Column(name = "status")
    var status: Int? = null

    @Column(name = "acessos")
    var acessos: Int? = null

    @Column(name = "descricao", length = 270)
    var descricao: String? = null

    @Column(name = "tipo_usuario")
    var tipoUsuario: Int? = null

    @Column(name = "canal")
    var canal: Int? = null
}
