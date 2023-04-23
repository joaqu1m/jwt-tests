package manuall.newproject.controller

import manuall.newproject.model.DescServicos
import manuall.newproject.repository.DescServicosRepository
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/descServicos")
class DescServicosController(
        val descServicosRepository: DescServicosRepository
) {

    @GetMapping("/{id}")
    fun buscarDescServicosPorId(@PathVariable id: Int): DescServicos? {
        return descServicosRepository.findById(id).orElseThrow()
    }
}