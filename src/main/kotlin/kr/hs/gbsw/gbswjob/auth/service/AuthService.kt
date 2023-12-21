package kr.hs.gbsw.gbswjob.auth.service

import kr.hs.gbsw.gbswjob.auth.JwtUtils
import kr.hs.gbsw.gbswjob.auth.authentication.IdPwAuthentication
import kr.hs.gbsw.gbswjob.auth.authentication.dto.LoginDto
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.core.Authentication
import org.springframework.stereotype.Service

@Service
class AuthService(
        private val authenticationManager: AuthenticationManager,
        private val jwtUtils: JwtUtils
) {
    fun login(dto: LoginDto): String {
        val result = authenticationManager.authenticate(IdPwAuthentication(dto.id, dto.pw, emptyList()))
        return createJwtToken(result)
    }

    fun createJwtToken(authentication: Authentication): String {
        val userId = authentication.principal as String
        val authorities = authentication.authorities

        return jwtUtils.createJwt(userId, authorities) //token
    }
}