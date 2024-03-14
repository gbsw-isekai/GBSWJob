package kr.hs.gbsw.gbswjob.company.domain

import com.fasterxml.jackson.annotation.JsonIgnore
import jakarta.persistence.*
import kr.hs.gbsw.gbswjob.user.domain.User
import java.time.LocalDateTime

@Entity
@Table(name = "company_comments")
class CompanyComment(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Int?,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "writer_id")
    var writer: User,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "company_id")
    @JsonIgnore
    var company: Company,

    @Column(columnDefinition = "TEXT")
    var content: String,

    var createdAt: LocalDateTime,
    var updatedAt: LocalDateTime
){
}