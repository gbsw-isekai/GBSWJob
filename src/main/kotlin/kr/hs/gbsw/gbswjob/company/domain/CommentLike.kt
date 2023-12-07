package kr.hs.gbsw.gbswjob.company.domain

import jakarta.persistence.*
import kr.hs.gbsw.gbswjob.user.domain.User

@Entity
@Table(name = "roles")
class CommentLike(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Int,
    @ManyToOne
    @JoinColumn(name = "user_id")
    var user: User,
    @ManyToOne
    @JoinColumn(name = "comment_id")
    var comment: Comment
) {
}