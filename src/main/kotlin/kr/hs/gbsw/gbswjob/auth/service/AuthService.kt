package kr.hs.gbsw.gbswjob.auth.service

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
        val result = authenticationManager.authenticate(IdPwAuthentication(id, pw, emptyList()))
        return createJwtToken(result)
    }

    fun createJwtToken(authentication: Authentication): String {
        val userId = authentication.principal as String
        val authorities = authentication.authorities

        val token = jwtUtils.createJwt(userId, authorities)

        return token
    }
}