package kr.hs.gbsw.gbswjob.auth

import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jws
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import org.springframework.beans.factory.annotation.Value
import org.springframework.security.core.GrantedAuthority
import org.springframework.stereotype.Component


@Component
class JwtUtils(
        @Value("\${jwt.key}") private val key: String
) {
    fun parseClaimsJws(token: String): Jws<Claims> {
        return Jwts.parserBuilder()
            .setSigningKey(key)
            .build()
            .parseClaimsJws(token)
    }

    fun validate(token: String): Boolean {
        return try {
            parseClaimsJws(token)
            true
        } catch (e: Exception) {
            false
        }
    }

    fun createJwt(userId: String, authorities: Collection<GrantedAuthority>): String {
        val header = mapOf("alg" to "HS512", "typ" to "JWT")

        return Jwts.builder()
                .setHeader(header)
                .setSubject(userId)
                .claim("roles", authorities.map { it.authority })
                .signWith(SignatureAlgorithm.HS512, key)
                .compact()
    }



    // 사용자 아이디 가져 오기
    fun getUserId(token: String): String {
        return parseClaimsJws(token).body.subject
    }

    fun getRoles(token: String): List<String> {
        return parseClaimsJws(token).body.get("roles", List::class.java) as List<String>
    }
}