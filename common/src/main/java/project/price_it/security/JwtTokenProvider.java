package project.price_it.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;

@Component
public class JwtTokenProvider {
    @Value("${jwt.secret}")
    private String secretKey;

    @Value("${jwt.access-token-validity-ms}")
    private long accessTokenValidity;

    @Value("${jwt.refresh-token-validity-ms}")
    private long refreshTokenValidity;

    public String createAccessToken(String userId) {
        Date now = new Date();
        Date expiry = new Date(now.getTime() + accessTokenValidity);

        return Jwts.builder()
                .setSubject(userId)
                .setIssuedAt(now)
                .setExpiration(expiry)
                .signWith(SignatureAlgorithm.HS256, secretKey.getBytes())
                .compact();
    }

    public String createRefreshToken(String userId) {
        Date now = new Date();
        Date expiry = new Date(now.getTime() + refreshTokenValidity);

        SecretKey key = Keys.hmacShaKeyFor(secretKey.getBytes());

        return Jwts.builder()
                .setSubject(userId)
                .setIssuedAt(now)
                .setExpiration(expiry)
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    public String refreshAccessToken(String refreshToken) {
        try {
            Claims claims = Jwts.parser()
                    .setSigningKey(secretKey.getBytes())
                    .parseClaimsJws(refreshToken)
                    .getBody();

            String userId = claims.getSubject();
            return createAccessToken(userId);
        } catch (Exception e) {
            throw new IllegalArgumentException("토큰 유효하지 않음", e);
        }
    }


    public String getUserIdFromAccessToken(String accessToken) {
        Claims claims = Jwts.parser()
                .setSigningKey(secretKey.getBytes())
                .parseClaimsJws(accessToken)
                .getBody();
        return claims.getSubject();
    }
}
