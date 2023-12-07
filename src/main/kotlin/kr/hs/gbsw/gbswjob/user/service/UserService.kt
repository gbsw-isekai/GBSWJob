package kr.hs.gbsw.gbswjob.user.service

import kr.hs.gbsw.gbswjob.user.domain.User
import kr.hs.gbsw.gbswjob.user.repository.UserRepository
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import java.lang.IllegalArgumentException

@Service
class UserService(
        private val repository: UserRepository,
        private val passwordEncoder: PasswordEncoder
) {
    fun register(id: String, pw: String): User {
        val user = User(id, passwordEncoder.encode(pw), emptyList())
        return repository.save(user)
    }

    fun getUser(id: String): User {
        return repository.findById(id).orElseThrow {
            IllegalArgumentException("존재하지 않는 사용자 입니다.")
        }
    }
}