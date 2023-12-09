package kr.hs.gbsw.gbswjob.company.domain

import com.fasterxml.jackson.annotation.JsonIgnore
import jakarta.persistence.*
import kr.hs.gbsw.gbswjob.user.domain.User

@Entity
@Table(
    uniqueConstraints = [
        UniqueConstraint(columnNames = ["user_id", "comment_id"])
    ]
)
class CompanyCommentLike(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Int?,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    var user: User,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "comment_id")
    @JsonIgnore
    var comment: CompanyComment
) {
}