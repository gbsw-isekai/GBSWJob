package kr.hs.gbsw.gbswjob.auth

import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jws
import io.jsonwebtoken.Jwts
import org.springframework.beans.factory.annotation.Value
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

    // 사용자 아이디 가져오기
    fun getUserId(token: String): String {
        return parseClaimsJws(token).body.subject
    }







}