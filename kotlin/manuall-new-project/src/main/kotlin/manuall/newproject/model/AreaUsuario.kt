package manuall.newproject.model

import jakarta.persistence.*

@Entity
@Table(name = "area_usuario")
class AreaUsuario {

    @field:Id
    @field:GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    var id: Int = 0

    @ManyToOne
    var usuario: Usuario = Usuario()

    @ManyToOne
    var area: Area = Area()
}