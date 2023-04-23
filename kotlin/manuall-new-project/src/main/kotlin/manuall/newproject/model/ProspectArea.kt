package manuall.newproject.model

import jakarta.persistence.*

@Entity
@Table(name = "prospect_area")
class ProspectArea {

        @field:Id
        @field:GeneratedValue(strategy = GenerationType.IDENTITY)
        @Column(name = "id")
        var id: Int = 0

        @ManyToOne
        var prospect: Prospect = Prospect()

        @ManyToOne
        var area: Area = Area()
}