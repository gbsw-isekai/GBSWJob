package kr.hs.gbsw.gbswjob.board.domain

import com.fasterxml.jackson.annotation.JsonIgnore
import jakarta.persistence.*
import kr.hs.gbsw.gbswjob.user.domain.User

@Entity
class BoardView(
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        var id: Int?,
        @ManyToOne(fetch = FetchType.LAZY)
        @JoinColumn(name = "user_id")
        var user: User?,
        @ManyToOne(fetch = FetchType.LAZY)
        @JoinColumn(name = "board_id")
        @JsonIgnore
        var board: Board,
) {
}