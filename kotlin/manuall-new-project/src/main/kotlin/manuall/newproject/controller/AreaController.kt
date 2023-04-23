package manuall.newproject.controller

import manuall.newproject.model.Area
import manuall.newproject.repository.AreaRepository
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/areas")
class AreaController(
        val areaRepository: AreaRepository
) {

    @GetMapping("/{id}")
    fun buscarAreaPorId(@PathVariable id: Int): Area? {
        return areaRepository.findById(id).orElseThrow()
    }
}