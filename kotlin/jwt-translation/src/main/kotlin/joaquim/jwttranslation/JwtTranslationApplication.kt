package joaquim.jwttranslation

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class JwtTranslationApplication

fun main(args: Array<String>) {
	runApplication<JwtTranslationApplication>(*args)
}
