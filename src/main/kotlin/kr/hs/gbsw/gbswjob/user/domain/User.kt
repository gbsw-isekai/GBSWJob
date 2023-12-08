package kr.hs.gbsw.gbswjob.user.domain

import com.fasterxml.jackson.annotation.JsonIgnore
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.ManyToMany
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority

@Entity
class User(
        @Id
        var id: String,
        @JsonIgnore
        var pw: String,

        @ManyToMany
        var roles: MutableList<Role>
) {
        fun generateGrantedAuthorities(): List<GrantedAuthority> {
                return roles.map {
                        SimpleGrantedAuthority(it.id)
                }
        }
}