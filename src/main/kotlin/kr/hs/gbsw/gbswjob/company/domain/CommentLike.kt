package kr.hs.gbsw.gbswjob.company.domain

import jakarta.persistence.*
import kr.hs.gbsw.gbswjob.user.domain.User

@Entity
class CommentLike(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Int?,
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    var user: User,
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "comment_id")
    var comment: Comment
) {
}