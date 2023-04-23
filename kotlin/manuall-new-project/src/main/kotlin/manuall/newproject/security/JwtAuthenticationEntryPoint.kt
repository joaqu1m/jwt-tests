package manuall.newproject.security

import jakarta.servlet.ServletException
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.security.authentication.BadCredentialsException
import org.springframework.security.core.AuthenticationException
import org.springframework.security.web.AuthenticationEntryPoint
import org.springframework.stereotype.Component
import java.io.IOException

@Component
class JwtAuthenticationEntryPoint: AuthenticationEntryPoint {

    @Throws(IOException::class, ServletException::class)
    override fun commence(
        request: HttpServletRequest,
        response: HttpServletResponse,
        authException: AuthenticationException
    ) {
        if (authException.javaClass == BadCredentialsException::class.java) {
            response.sendError(HttpServletResponse.SC_FORBIDDEN)
        } else {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED)
        }
    }
}