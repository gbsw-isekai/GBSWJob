package kr.hs.gbsw.gbswjob.company.domain

import jakarta.persistence.*
import kr.hs.gbsw.gbswjob.user.domain.User
import java.time.LocalDateTime

class Comment(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Int?,
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "writer_id")
    var writer: User,
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "company_id")
    var company: Company,
    var content: String,
    var like: Int,
    var createdAt: LocalDateTime,
    var updatedAt: LocalDateTime
){
}