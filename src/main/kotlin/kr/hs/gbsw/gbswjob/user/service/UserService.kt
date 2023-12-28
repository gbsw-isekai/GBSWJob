package kr.hs.gbsw.gbswjob.user.service

import kr.hs.gbsw.gbswjob.user.domain.Role
import kr.hs.gbsw.gbswjob.user.domain.User
import kr.hs.gbsw.gbswjob.user.dto.UserRegisterDto
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
    companion object {
        const val PROFILE = "https://picpac.kr/common/img/default_profile.png"
    }
    fun register(dto: UserRegisterDto): User {
        val role = roleRepository.findById("USER").orElseGet {
            roleRepository.save(Role("USER", "사용자"))
        }

        if (!repository.existsById(dto.id)) {
            throw IllegalArgumentException("이미 "+ dto.id + "라는 유저가 존재합니다.")
        }

        val user = User(
            dto.id,
            passwordEncoder.encode(dto.pw),
            dto.name,
            //프로필 여부 확인
            dto.number,
            dto.profile!!.ifEmpty {
                PROFILE
            },
            mutableListOf(role)
        )

        return repository.save(user)
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