package kr.hs.gbsw.gbswjob.user.repository

import kr.hs.gbsw.gbswjob.user.domain.Role
import org.springframework.data.jpa.repository.JpaRepository

interface RoleRepository : JpaRepository<Role, String>{
}