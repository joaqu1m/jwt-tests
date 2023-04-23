package manuall.newproject.repository

import manuall.newproject.model.DescServicos
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface DescServicosRepository:JpaRepository<DescServicos, Int> {

    fun deleteByUsuarioId(id: Int)
}