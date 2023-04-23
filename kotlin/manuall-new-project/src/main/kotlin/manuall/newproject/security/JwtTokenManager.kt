package manuall.newproject.security

import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.security.Keys
import org.springframework.beans.factory.annotation.Value
import org.springframework.security.core.Authentication
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import java.nio.charset.StandardCharsets
import java.util.*
import java.util.function.Function
import java.util.stream.Collectors
import javax.crypto.SecretKey

class JwtTokenManager {

    @Value("\${jwt.secret}")
    private val secret: String? = null

    @Value("\${jwt.validity}")
    private val jwtTokenValidity: Long = 0

    fun getUsernameFromToken(token: String): String {
        return getClaimForToken(token) { obj: Claims -> obj.subject }
    }

    private fun getExpirationDateFromToken(token: String?): Date {
        return getClaimForToken(
            token
        ) { obj: Claims -> obj.expiration }
    }

    fun generateToken(authentication: Authentication): String {

        authentication.authorities.stream().map { obj: GrantedAuthority -> obj.authority }
            .collect(Collectors.joining(","))
        return Jwts.builder().setSubject(authentication.name)
            .signWith(parseSecret()).setIssuedAt(Date(System.currentTimeMillis()))
            .setExpiration(Date(System.currentTimeMillis() + jwtTokenValidity * 1000)).compact()
    }

    private fun <T> getClaimForToken(token: String?, claimsResolver: Function<Claims, T>): T {
        val claims = getAllClaimsFromToken(token)
        return claimsResolver.apply(claims)
    }

    fun validateToken(token: String, userDetails: UserDetails): Boolean {
        val username = getUsernameFromToken(token)
        return username == userDetails.username && !isTokenExpired(token)
    }

    private fun isTokenExpired(token: String?): Boolean {
        val expirationDate = getExpirationDateFromToken(token)
        return expirationDate.before(Date(System.currentTimeMillis()))
    }

    private fun getAllClaimsFromToken(token: String?): Claims {
        return Jwts.parserBuilder()
            .setSigningKey(parseSecret())
            .build()
            .parseClaimsJws(token).body
    }

    private fun parseSecret(): SecretKey {
        return Keys.hmacShaKeyFor(secret!!.toByteArray(StandardCharsets.UTF_8))
    }
}