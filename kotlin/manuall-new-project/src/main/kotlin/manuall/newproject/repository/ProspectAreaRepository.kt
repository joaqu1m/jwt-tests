package manuall.newproject.repository

import manuall.newproject.model.ProspectArea
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface ProspectAreaRepository : JpaRepository<ProspectArea, Int> {
}