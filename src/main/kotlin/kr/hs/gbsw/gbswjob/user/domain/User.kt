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
        var id: String, //사용자 id
        @JsonIgnore
        var pw: String, // 사용자 비밀번호
        var name: String, // 사용자 이름
        var number: String, //사용자 전화번호
        var profile: String, //사용자 프로필

        @ManyToMany
        var roles: MutableList<Role>
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as User

        if (id != other.id) return false

        return true
    }

    override fun hashCode(): Int {
        return id.hashCode()
    }

    fun generateGrantedAuthorities(): List<GrantedAuthority> {
            return roles.map {
                    SimpleGrantedAuthority("ROLE_${it.id}")
            }
    }
}