package kr.hs.gbsw.gbswjob.user.domain

import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.ManyToMany
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority

@Entity
class User(
        @Id var id: String,
        var pw: String,
) {
}