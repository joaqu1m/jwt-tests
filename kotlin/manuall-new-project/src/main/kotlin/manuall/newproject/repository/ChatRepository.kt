package manuall.newproject.repository

import manuall.newproject.model.Chat
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface ChatRepository: JpaRepository<Chat, Int> {
}