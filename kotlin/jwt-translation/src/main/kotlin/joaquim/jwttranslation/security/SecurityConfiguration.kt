package joaquim.jwttranslation.security

import jakarta.servlet.http.HttpServletRequest
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpMethod
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.config.Customizer
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configurers.AuthorizeHttpRequestsConfigurer.AuthorizationManagerRequestMatcherRegistry
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
class SecurityConfiguration {
    @Autowired
    private val autenticacaoService: AutenticacaoService? = null

    @Autowired
    private val autenticacaoJwtEntryPoint: AutenticacaoEntryPoint? = null
    @Bean
    @Throws(Exception::class)
    fun filterChain(http: HttpSecurity): SecurityFilterChain {
        http.headers()
            .frameOptions().disable()
            .and()
            .cors()
            .configurationSource { request: HttpServletRequest? -> buildCorsConfiguration() }
            .and()
            .csrf()
            .disable()
            .authorizeHttpRequests(
                Customizer { authorize ->
                    authorize.requestMatchers(*URLS_PERMITIDAS)
                        .permitAll()
                        .anyRequest()
                        .authenticated()
                }
            )
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
            AutenticacaoProvider(
                autenticacaoService,
                passwordEncoder()
            )
        )
        return authenticationManagerBuilder.build()
    }

    @Bean
    fun jwtAuthenticationEntryPointBean(): AutenticacaoEntryPoint {
        return AutenticacaoEntryPoint()
    }

    @Bean
    fun jwtAuthenticationFilterBean(): AutenticacaoFilter {
        return AutenticacaoFilter(autenticacaoService, jwtAuthenticationUtilBean())
    }

    @Bean
    fun jwtAuthenticationUtilBean(): GerenciadorTokenJwt {
        return GerenciadorTokenJwt()
    }

    @Bean
    fun passwordEncoder(): PasswordEncoder {
        return BCryptPasswordEncoder()
    }

    private fun buildCorsConfiguration(): CorsConfiguration {
        val configuration = CorsConfiguration()
        configuration.allowedOriginPatterns = listOf(ORIGENS_PERMITIDAS)
        configuration.allowedMethods = Arrays.asList(
            HttpMethod.GET.name(),
            HttpMethod.POST.name(),
            HttpMethod.PUT.name(),
            HttpMethod.DELETE.name()
        )
        configuration.allowedHeaders =
            Arrays.asList(HttpHeaders.CONTENT_TYPE, HttpHeaders.AUTHORIZATION)
        return configuration
    }

    companion object {
        private const val ORIGENS_PERMITIDAS = "*"
        private val URLS_PERMITIDAS = arrayOf(
            AntPathRequestMatcher("/swagger-ui/**"),
            AntPathRequestMatcher("/swagger-ui.html"),
            AntPathRequestMatcher("/swagger-resources"),
            AntPathRequestMatcher("/swagger-resources/**"),
            AntPathRequestMatcher("/configuration/ui"),
            AntPathRequestMatcher("/configuration/security"),
            AntPathRequestMatcher("/api/public/**"),
            AntPathRequestMatcher("/api/public/authenticate"),
            AntPathRequestMatcher("/webjars/**"),
            AntPathRequestMatcher("/v3/api-docs/**"),
            AntPathRequestMatcher("/actuator/*"),
            AntPathRequestMatcher("/usuarios/login/**"),
            AntPathRequestMatcher("/h2-console/**"),
            AntPathRequestMatcher("/error/**")
        )
    }
}