package manuall.newproject.model

import jakarta.persistence.*

@Entity
@Table(name = "solicitacao")
class Solicitacao {

    @field:Id
    @field:GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    var id: Int = 0

    @ManyToOne
    var contratanteUsuario: Usuario = Usuario()

    @ManyToOne
    var prestadorUsuario: Usuario = Usuario()

    @Column(name = "assunto", length = 30)
    var assunto: String? = null

    @Column(name = "mensagem", length = 120)
    var mensagem: String? = null

    @Column(name = "status")
    var status: Int? = null

    @Column(name = "tipo_servico")
    var tipoServico: Int? = null
}