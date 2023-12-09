package kr.hs.gbsw.gbswjob.board.domain

import com.fasterxml.jackson.annotation.JsonIgnore
import jakarta.persistence.*
import kr.hs.gbsw.gbswjob.user.domain.User
import java.time.LocalDateTime

@Entity
@Table(name = "board_comments")
class BoardComment(
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        var id: Int?,
        var content: String,
        @ManyToOne(fetch = FetchType.LAZY)
        @JoinColumn(name = "writer_id")
        var writer: User,
        @ManyToOne(fetch = FetchType.LAZY)
        @JoinColumn(name = "board_id")
        @JsonIgnore
        var board: Board,
        @OneToMany(mappedBy = "comment")
        var like: MutableList<BoardCommentLike>?,
        var createdAt: LocalDateTime,
        var updatedAt: LocalDateTime
) {
}