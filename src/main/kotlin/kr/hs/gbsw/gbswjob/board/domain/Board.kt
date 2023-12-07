package kr.hs.gbsw.gbswjob.board.domain

import jakarta.persistence.*
import kr.hs.gbsw.gbswjob.user.domain.User
import java.time.LocalDateTime

@Entity
class Board(
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        var id: Int?,
        var title: String,
        var content: String,
        @ManyToOne(fetch = FetchType.LAZY)
        @JoinColumn(name = "writer_id")
        var writer: User,      // 다대1
        var createdAt: LocalDateTime,
        var updatedAt: LocalDateTime
) {
}

//class User(
//
//        @OneToMany(mappedBy = "writer")       // mappedBy를 쓰는건 주인 관계를 설정할려고. (지금은 이제 Board 가 주인)
//        var boards: List<Board>         // 1대다
//)