package manuall.newproject.repository

import manuall.newproject.model.Avaliacao
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface AvaliacaoRepository : JpaRepository<Avaliacao, Int> {
}