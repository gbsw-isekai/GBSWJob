package kr.hs.gbsw.gbswjob.board.domain

import com.fasterxml.jackson.annotation.JsonIgnore
import jakarta.persistence.*
import kr.hs.gbsw.gbswjob.user.domain.User
import org.hibernate.annotations.Cascade
import java.time.LocalDateTime

@Entity
class Board(
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        var id: Int?,
        var title: String?,
        @Column(columnDefinition = "TEXT")
        var content: String,
        @ManyToOne(fetch = FetchType.LAZY)
        @JoinColumn(name = "writer_id")
        var writer: User,      // 다대1
        @OneToMany(mappedBy = "question", cascade = [CascadeType.REMOVE])
        var answers: MutableList<Board>?,
        @JsonIgnore
        @ManyToOne(fetch = FetchType.LAZY)
        var question: Board?,
        @OneToMany(mappedBy = "board", cascade = [CascadeType.REMOVE])
        var comment: MutableList<BoardComment>?,
        @OneToMany(mappedBy = "board", cascade = [CascadeType.REMOVE])
        var like: MutableList<BoardLike>?,
        @OneToMany(mappedBy = "board", cascade = [CascadeType.REMOVE])
        var view: MutableList<BoardView>?,
        var viewCount: Int,
        var createdAt: LocalDateTime,
        var updatedAt: LocalDateTime
) {
    fun isAnswer(): Boolean {
        return question != null
    }

    fun isQuestion(): Boolean {
        return question == null
    }
}

//class User(
//
//        @OneToMany(mappedBy = "writer")       // mappedBy를 쓰는건 주인 관계를 설정할려고. (지금은 이제 Board 가 주인)
//        var boards: List<Board>         // 1대다
//)