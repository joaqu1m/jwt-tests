package manuall.newproject.controller

import manuall.newproject.model.Chat
import manuall.newproject.repository.ChatRepository
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/chats")
class ChatController (
    val chatRepository: ChatRepository
) {

    @GetMapping("/{id}")
    fun buscarChatPorId(@PathVariable id: Int): Chat? {
        return chatRepository.findById(id).orElseThrow()
    }
}