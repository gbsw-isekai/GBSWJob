package kr.hs.gbsw.gbswjob.auth.authentication.service

import io.jsonwebtoken.Jwts
import kr.hs.gbsw.gbswjob.auth.JwtUtils
import kr.hs.gbsw.gbswjob.auth.authentication.IdPwAuthentication
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.core.Authentication
import org.springframework.stereotype.Service

@Service
class AuthService(
        private val authenticationManager: AuthenticationManager,
        private val jwtUtils: JwtUtils
) {
    fun login(id: String, pw: String): String {
        val result = authenticationManager.authenticate(IdPwAuthentication(id, pw))
        return createJwtToken(result)
    }

    fun createJwtToken(authentication: Authentication): String {
        val userId = authentication.principal as String

        //JWT 생성

        var data = mapOf<String, String>("userid" to userId);

        val token = jwtUtils.createJwt(data);

        return token
    }
}