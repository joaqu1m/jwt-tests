package manuall.newproject.repository

import manuall.newproject.model.Prospect
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface ProspectRepository : JpaRepository<Prospect, Int> {

    fun findByEmail(email: String):Prospect?
}