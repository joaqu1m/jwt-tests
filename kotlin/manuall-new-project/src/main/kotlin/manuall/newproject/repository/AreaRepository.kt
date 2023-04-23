package manuall.newproject.repository

import manuall.newproject.model.Area
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface AreaRepository: JpaRepository<Area, Int> {
}