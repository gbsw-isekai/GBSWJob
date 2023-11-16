package kr.hs.gbsw.gbswjob.user.repository

import kr.hs.gbsw.gbswjob.user.domain.User
import org.springframework.data.jpa.repository.JpaRepository

interface UserRepository : JpaRepository<User, String> {
}