package manuall.newproject.controller

import manuall.newproject.model.DadosEndereco
import manuall.newproject.repository.DadosEnderecoRepository
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/dadosEnderecos")
class DadosEnderecoController(
        val dadosEnderecoRepository: DadosEnderecoRepository
) {

    @GetMapping("/{id}")
    fun buscarDadosEnderecoPorId(@PathVariable id: Int): DadosEndereco? {
        return dadosEnderecoRepository.findById(id).orElseThrow()
    }
}