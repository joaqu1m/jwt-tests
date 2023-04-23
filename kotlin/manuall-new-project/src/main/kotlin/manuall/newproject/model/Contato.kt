package manuall.newproject.model

import jakarta.persistence.*

@Entity
@Table(name = "contato")
class Contato {

    @field:Id
    @field:GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    var id: Int = 0

    @Column(name = "nome", length = 60)
    var nome: String? = null

    @Column(name = "email", length = 256)
    var email: String? = null

    @Column(name = "mensagem", length = 120)
    var mensagem: String? = null
}