package manuall.newproject.config

import manuall.newproject.security.*
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpMethod
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import org.springframework.security.web.util.matcher.AntPathRequestMatcher
import org.springframework.web.cors.CorsConfiguration
import java.util.*

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
class SecurityConfig (
    val jwtAuthenticationService: JwtAuthenticationService,
    val autenticacaoJwtEntryPoint: JwtAuthenticationEntryPoint
) {

    companion object {
        private const val ORIGENS_PERMITIDAS = "*"
        private val URLS_PERMITIDAS = arrayOf(
            AntPathRequestMatcher("/swagger-ui/**"),
            AntPathRequestMatcher("/swagger-ui.html"),
            AntPathRequestMatcher("/swagger-resources"),
            AntPathRequestMatcher("/swagger-resources/**"),
            AntPathRequestMatcher("/v3/api-docs/**"),
            AntPathRequestMatcher("/areas/**"),
            AntPathRequestMatcher("/areausuario/**"),
            AntPathRequestMatcher("/avaliacoes/**"),
            AntPathRequestMatcher("/chats/**"),
            AntPathRequestMatcher("/contatos/**"),
            AntPathRequestMatcher("/dadosBancarios/**"),
            AntPathRequestMatcher("/dadosEnderecos/**"),
            AntPathRequestMatcher("/descServicos/**"),
            AntPathRequestMatcher("/prospects/**"),
            AntPathRequestMatcher("/solicitacoes/**"),
            AntPathRequestMatcher("/areas/**"),
            AntPathRequestMatcher("/areas/**"),
            AntPathRequestMatcher("/usuarios/login/**"),
            AntPathRequestMatcher("/usuarios/cadastrar/**"),
            AntPathRequestMatcher("/usuarios/checar/**"),
            AntPathRequestMatcher("/usuarios/example/sem-token")
        )
    }

    @Bean
    @Throws(Exception::class)
    fun filterChain(http: HttpSecurity): SecurityFilterChain {
        http.headers()
            .frameOptions().disable()
            .and()
            .cors()
            .configurationSource { buildCorsConfiguration() }
            .and()
            .csrf()
            .disable()
            .authorizeHttpRequests { authorize ->
                authorize.requestMatchers(*URLS_PERMITIDAS)
                    .permitAll()
                    .anyRequest()
                    .authenticated()
            }
            .exceptionHandling()
            .authenticationEntryPoint(autenticacaoJwtEntryPoint)
            .and()
            .sessionManagement()
            .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        http.addFilterBefore(jwtAuthenticationFilterBean(), UsernamePasswordAuthenticationFilter::class.java)
        return http.build()
    }

    @Bean
    @Throws(Exception::class)
    fun authManager(http: HttpSecurity): AuthenticationManager {
        val authenticationManagerBuilder = http.getSharedObject(
            AuthenticationManagerBuilder::class.java
        )
        authenticationManagerBuilder.authenticationProvider(
            JwtAuthenticationProvider(
                jwtAuthenticationService,
                passwordEncoder()
            )
        )
        return authenticationManagerBuilder.build()
    }

    @Bean
    fun jwtAuthenticationEntryPointBean(): JwtAuthenticationEntryPoint {
        return JwtAuthenticationEntryPoint()
    }

    @Bean
    fun jwtAuthenticationFilterBean(): JwtAuthenticationFilter {
        return JwtAuthenticationFilter(jwtAuthenticationService, jwtAuthenticationUtilBean())
    }

    @Bean
    fun jwtAuthenticationUtilBean(): JwtTokenManager {
        return JwtTokenManager()
    }

    @Bean
    fun passwordEncoder(): PasswordEncoder {
        return BCryptPasswordEncoder()
    }

    private fun buildCorsConfiguration(): CorsConfiguration {
        val configuration = CorsConfiguration()
        configuration.allowedOriginPatterns = listOf(ORIGENS_PERMITIDAS)
        configuration.allowedMethods = listOf(
            HttpMethod.GET.name(),
            HttpMethod.POST.name(),
            HttpMethod.PUT.name(),
            HttpMethod.DELETE.name()
        )
        configuration.allowedHeaders =
            listOf(HttpHeaders.CONTENT_TYPE, HttpHeaders.AUTHORIZATION)
        return configuration
    }
}