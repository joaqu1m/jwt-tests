package manuall.newproject.controller

import manuall.newproject.model.Contato
import manuall.newproject.repository.ContatoRepository
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/contatos")
class ContatoController(
        val contatoRepository: ContatoRepository
) {

    @GetMapping("/{id}")
    fun buscarContatoPorId(@PathVariable id: Int): Contato? {
        return contatoRepository.findById(id).orElseThrow()
    }
}