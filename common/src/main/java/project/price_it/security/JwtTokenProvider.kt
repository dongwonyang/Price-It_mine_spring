package project.price_it.security

import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import io.jsonwebtoken.security.Keys
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import java.util.Date
import javax.crypto.SecretKey

@Component
class JwtTokenProvider(
        @Value("\${jwt.secret}") private val secretKey: String,
        @Value("\${jwt.access-token-validity-ms}") private val accessTokenValidity: Long,
        @Value("\${jwt.refresh-token-validity-ms}") private val refreshTokenValidity: Long
) {

    fun createAccessToken(userId: String): String {
        val now = Date()
        val expiry = Date(now.time + accessTokenValidity)

        return Jwts.builder()
                .setSubject(userId)
                .setIssuedAt(now)
                .setExpiration(expiry)
                .signWith(SignatureAlgorithm.HS256, secretKey.toByteArray())
                .compact()
    }

    fun createRefreshToken(userId: String): String {
        val now = Date()
        val expiry = Date(now.time + refreshTokenValidity)
        val key: SecretKey = Keys.hmacShaKeyFor(secretKey.toByteArray())

        return Jwts.builder()
                .setSubject(userId)
                .setIssuedAt(now)
                .setExpiration(expiry)
                .signWith(key, SignatureAlgorithm.HS256)
                .compact()
    }

    fun refreshAccessToken(refreshToken: String): String {
        try {
            val claims: Claims = Jwts.parser()
                    .setSigningKey(secretKey.toByteArray())
                    .parseClaimsJws(refreshToken)
                    .body

            val userId = claims.subject
            return createAccessToken(userId)
        } catch (e: Exception) {
            throw IllegalArgumentException("토큰 유효하지 않음", e)
        }
    }

    fun getUserIdFromAccessToken(accessToken: String): String {
        val claims: Claims = Jwts.parser()
                .setSigningKey(secretKey.toByteArray())
                .parseClaimsJws(accessToken)
                .body

        return claims.subject
    }
}
