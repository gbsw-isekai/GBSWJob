package kr.hs.gbsw.gbswjob.user.domain

import jakarta.persistence.Entity
import jakarta.persistence.Id

@Entity
class User(
        @Id var id: String,
        var pw: String,
) {
}