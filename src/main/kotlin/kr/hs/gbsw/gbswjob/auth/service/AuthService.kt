package kr.hs.gbsw.gbswjob.auth.service

import kr.hs.gbsw.gbswjob.auth.JwtUtils
import kr.hs.gbsw.gbswjob.auth.authentication.IdPwAuthentication
import kr.hs.gbsw.gbswjob.auth.authentication.dto.LoginDto
import kr.hs.gbsw.gbswjob.common.AuthUserId
import kr.hs.gbsw.gbswjob.user.domain.Role
import kr.hs.gbsw.gbswjob.user.domain.User
import kr.hs.gbsw.gbswjob.user.dto.UserRegisterDto
import kr.hs.gbsw.gbswjob.user.repository.RoleRepository
import kr.hs.gbsw.gbswjob.user.repository.UserRepository
import kr.hs.gbsw.gbswjob.user.service.UserService
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.core.Authentication
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service

@Service
class AuthService(
    private val authenticationManager: AuthenticationManager,
    private val roleRepository: RoleRepository,
    private val passwordEncoder: PasswordEncoder,
    private val jwtUtils: JwtUtils,
    private val repository: UserRepository,
) {
    companion object {
        const val PROFILE = "https://picpac.kr/common/img/default_profile.png"
    }

    fun login(dto: LoginDto): String {
        val result = authenticationManager.authenticate(IdPwAuthentication(dto.id, dto.pw, emptyList()))
        return createJwtToken(result)
    }

    fun register(dto: UserRegisterDto, userId: String?): String {
        if (userId != null && !repository.existsById(userId)) {
            throw IllegalArgumentException("이미 로그인이 되어 있습니다.")
        }

        val role = roleRepository.findById("USER").orElseGet {
            roleRepository.save(Role("USER", "사용자"))
        }


        if (repository.existsById(dto.id)) {
            throw IllegalArgumentException("이미 "+ dto.id + "라는 유저가 존재합니다.")
        }

        val user = User(
            dto.id,
            passwordEncoder.encode(dto.pw),
            dto.name,
            //프로필 여부 확인
            dto.number,
            dto.profile!!.ifEmpty {
                UserService.PROFILE
            },
            mutableListOf(role)
        )

        repository.save(user)

        val result = authenticationManager.authenticate(IdPwAuthentication(dto.id, dto.pw, emptyList()))

        return createJwtToken(result)
    }


    fun createJwtToken(authentication: Authentication): String {
        val userId = authentication.principal as String
        val authorities = authentication.authorities

        return jwtUtils.createJwt(userId, authorities) //token
    }
}