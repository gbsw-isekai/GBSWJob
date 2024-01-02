package kr.hs.gbsw.gbswjob.user.service

import kr.hs.gbsw.gbswjob.auth.authentication.IdPwAuthentication
import kr.hs.gbsw.gbswjob.user.domain.Role
import kr.hs.gbsw.gbswjob.user.domain.User
import kr.hs.gbsw.gbswjob.user.dto.UserRegisterDto
import kr.hs.gbsw.gbswjob.user.dto.UserUpdateDto
import kr.hs.gbsw.gbswjob.user.repository.RoleRepository
import kr.hs.gbsw.gbswjob.user.repository.UserRepository
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service

@Service
class UserService(
        private val repository: UserRepository,
        private val roleRepository: RoleRepository,
        private val passwordEncoder: PasswordEncoder,
        private val authenticationManager: AuthenticationManager
) {
    companion object {
        const val PROFILE = "https://picpac.kr/common/img/default_profile.png"
    }

    fun updateUser(dto: UserUpdateDto, userId: String): User {
        val user = repository.findById(userId).orElseThrow {
            IllegalArgumentException("유저가 존재하지 않습니다.")
        }

        user.name = dto.name
        user.profile = dto.profile.ifEmpty {
            PROFILE
        }
        user.pw = dto.pw

        return repository.save(user)
    }

    fun getUser(id: String): User {
        return repository.findById(id).orElseThrow {
            IllegalArgumentException("존재하지 않는 사용자 입니다.")
        }
    }

    fun getUsers(): List<User> {
        return repository.findAll()
    }
}