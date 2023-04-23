package manuall.newproject.security

import io.jsonwebtoken.ExpiredJwtException
import jakarta.servlet.FilterChain
import jakarta.servlet.ServletException
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.slf4j.LoggerFactory
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource
import org.springframework.web.filter.OncePerRequestFilter
import java.io.IOException
import java.util.*

class JwtAuthenticationFilter(
    private val jwtAuthenticationService: JwtAuthenticationService?,
    private val jwtTokenManager: JwtTokenManager
): OncePerRequestFilter() {

    @Throws(ServletException::class, IOException::class)
    override fun doFilterInternal (
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        var username: String? = null
        var jwtToken: String = ""
        val requestTokenHeader = request.getHeader("Authorization")
        if (Objects.nonNull(requestTokenHeader) && requestTokenHeader.startsWith("Bearer ")) {
            jwtToken = requestTokenHeader.substring(7)
            try {
                username = jwtTokenManager.getUsernameFromToken(jwtToken)
            } catch (exception: ExpiredJwtException) {
                LOGGER.info(
                    "[FALHA AUTENTICACAO] - Token expirado, usuario: {} - {}",
                    exception.claims.subject, exception.message
                )
                LOGGER.trace("[FALHA AUTENTICACAO] - stack trace: %s", exception)
                response.status = HttpServletResponse.SC_UNAUTHORIZED
            }
        }
        if (username != null && SecurityContextHolder.getContext().authentication == null) {
            addUsernameInContext(request, username, jwtToken)
        }
        filterChain.doFilter(request, response)
    }

    private fun addUsernameInContext(request: HttpServletRequest, username: String, jwtToken: String) {
        val userDetails: UserDetails = jwtAuthenticationService!!.loadUserByUsername(username)
        if (jwtTokenManager.validateToken(jwtToken, userDetails)) {
            val usernamePasswordAuthenticationToken = UsernamePasswordAuthenticationToken(
                userDetails, null, userDetails.authorities
            )
            usernamePasswordAuthenticationToken.details = WebAuthenticationDetailsSource().buildDetails(request)
            SecurityContextHolder.getContext().authentication = usernamePasswordAuthenticationToken
        }
    }

    companion object {
        private val LOGGER = LoggerFactory.getLogger(JwtAuthenticationFilter::class.java)
    }
}