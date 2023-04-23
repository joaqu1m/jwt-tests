package manuall.newproject.model

import java.sql.Date
import jakarta.persistence.*

@Entity
@Table(name = "dados_bancarios")
class DadosBancarios {

    @field:Id
    @field:GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    var id: Int = 0

    @Column(name = "plano")
    var plano: Int? = null

    @Column(name = "nome", length = 60)
    var nome: String? = null

    @Column(name = "numero", length = 16)
    var numero: String? = null

    @Column(name = "validade")
    var validade: Date? = null

    @Column(name = "cvv", length = 3)
    var cvv: String? = null

    @OneToOne
    var usuario: Usuario = Usuario()
}