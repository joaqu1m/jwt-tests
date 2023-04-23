package manuall.newproject.repository

import manuall.newproject.model.Solicitacao
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface SolicitacaoRepository:JpaRepository<Solicitacao, Int> {
}