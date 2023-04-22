package joaquim.jwttranslation.controller

import io.swagger.v3.oas.annotations.security.SecurityRequirement
import joaquim.jwttranslation.dto.UsuarioCriacaoDto
import joaquim.jwttranslation.dto.UsuarioLoginDto
import joaquim.jwttranslation.dto.UsuarioTokenDto
import joaquim.jwttranslation.service.UsuarioService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/usuarios")
class UsuarioController {
    @Autowired
    private val usuarioService: UsuarioService? = null
    @PostMapping
    @SecurityRequirement(name = "Bearer")
    fun criar(@RequestBody usuarioCriacaoDto: UsuarioCriacaoDto): ResponseEntity<Void> {
        usuarioService!!.criar(usuarioCriacaoDto)
        return ResponseEntity.status(201).build()
    }

    @PostMapping("/login")
    fun login(@RequestBody usuarioLoginDto: UsuarioLoginDto): ResponseEntity<UsuarioTokenDto> {
        val usuarioTokenDto: UsuarioTokenDto = usuarioService!!.autenticar(usuarioLoginDto)
        return ResponseEntity.status(200).body(usuarioTokenDto)
    }

    @PostMapping("/testando/sem-token")
    fun postTestandoSemToken():String {
        return "rota"
    }

    @SecurityRequirement(name = "Bearer")
    @PostMapping("/testando/com-token")
    fun postTestandoComToken():String {
        return "rota"
    }

    @GetMapping("/testando/sem-token")
    fun getTestandoSemToken():String {
        return "rota"
    }

    @SecurityRequirement(name = "Bearer")
    @GetMapping("/testando/com-token")
    fun getTestandoComToken():String {
        return "rota"
    }
}