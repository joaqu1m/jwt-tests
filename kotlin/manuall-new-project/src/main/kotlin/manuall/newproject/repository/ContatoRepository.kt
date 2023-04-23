package manuall.newproject.repository

import manuall.newproject.model.Contato
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface ContatoRepository:JpaRepository<Contato, Int> {
}