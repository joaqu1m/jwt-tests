package manuall.newproject.controller

import manuall.newproject.model.AreaUsuario
import manuall.newproject.repository.AreaUsuarioRepository
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/areausuario")
class AreaUsuarioController (
        val areaUsuarioRepository: AreaUsuarioRepository
) {

    @GetMapping("/{id}")
    fun buscarAreaUsuarioPorId(@PathVariable id: Int): AreaUsuario? {
        return areaUsuarioRepository.findById(id).orElseThrow()
    }
}