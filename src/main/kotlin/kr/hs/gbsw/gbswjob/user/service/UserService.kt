package kr.hs.gbsw.gbswjob.user.service

import kr.hs.gbsw.gbswjob.user.domain.Role
import kr.hs.gbsw.gbswjob.user.domain.User
import kr.hs.gbsw.gbswjob.user.dto.UserUpdateDto
import kr.hs.gbsw.gbswjob.user.repository.RoleRepository
import kr.hs.gbsw.gbswjob.user.repository.UserRepository
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service

@Service
class UserService(
        private val repository: UserRepository,
        private val roleRepository: RoleRepository,
        private val passwordEncoder: PasswordEncoder
) {
    fun register(userId: String, id: String, pw: String, name: String, number: String, profile: String): User {
        val role = roleRepository.findById("USER").orElseGet {
            roleRepository.save(Role("USER", "사용자"))
        }

        if (repository.findById(userId).isEmpty.not()) {
            throw IllegalArgumentException("이미 로그인이 되어 있습니다.")
        }

        var userProfile = profile

        if (userProfile.isEmpty()) {
            userProfile = "https://picpac.kr/common/img/default_profile.png"
        }

        val user = User(
            id,
            passwordEncoder.encode(pw),
            name,
            userProfile,
            number,
            mutableListOf(role)
        )

        return repository.save(user)
    }

    fun updateUser(dto: UserUpdateDto, userId: String): User {
        val user = repository.findById(userId).orElseThrow {
            IllegalArgumentException("유저가 존재하지 않습니다.")
        }

        if (dto.profile.isEmpty()) {
            dto.profile = "https://picpac.kr/common/img/default_profile.png"
        }

        user.name = dto.name
        user.profile = dto.profile
        user.pw = dto.pw

        return repository.save(user)
    }

    fun getUser(id: String): User {
        return repository.findById(id).orElseThrow {
            IllegalArgumentException("존재하지 않는 사용자 입니다.")
        }
    }

    fun getUsers(user: User): List<User> {
        if (!user.roles.equals("admin")) {
            throw IllegalArgumentException("권한이 없습니다.")
        }
        return repository.findAll()
    }
}