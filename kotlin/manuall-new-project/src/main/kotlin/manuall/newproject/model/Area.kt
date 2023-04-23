package manuall.newproject.model

import jakarta.persistence.*

@Entity
@Table(name = "area")
class Area {

    @field:Id
    @field:GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    var id: Int = 0

    @Column(name = "nome", length = 30)
    var nome: String? = null
}