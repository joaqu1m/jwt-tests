package manuall.newproject.model

import jakarta.persistence.*

@Entity
@Table(name = "dados_endereco")
class DadosEndereco {

    @field:Id
    @field:GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    var id: Int = 0

    @Column(name = "estado", length = 25)
    var estado: String? = null

    @Column(name = "cidade", length = 35)
    var cidade: String? = null

    @Column(name = "cep", length = 8)
    var cep: String? = null

    @Column(name = "bairro", length = 35)
    var bairro: String? = null

    @Column(name = "rua", length = 45)
    var rua: String? = null

    @Column(name = "numero")
    var numero: Int? = null

    @Column(name = "complemento", length = 25)
    var complemento: String? = null

    @ManyToOne
    var usuario: Usuario = Usuario()
}