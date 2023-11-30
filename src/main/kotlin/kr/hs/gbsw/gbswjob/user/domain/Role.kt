package kr.hs.gbsw.gbswjob.user.domain

import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table

// ADMIN <- Id
// USER <- Id

//id title description
//"ADMIN" "관리자" "관리자는 모든 권한을 가지고 있음"

@Entity
@Table(name = "roles")
class Role (
    @Id
    var id: String,
    var title: String
) {
}