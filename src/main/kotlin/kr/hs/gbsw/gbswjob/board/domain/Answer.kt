package kr.hs.gbsw.gbswjob.board.domain

import jakarta.persistence.*
import kr.hs.gbsw.gbswjob.user.domain.User
import java.time.LocalDateTime

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE) // 나중에 찾아보자
@DiscriminatorColumn(name = "type") // Discriminate : 차별? 차별 컬럼
@DiscriminatorValue(value = "answer")
class Answer(
    id: Int?,
    title: String?,
    content: String,
    writer: User,
    answers: MutableList<Board>?,
    answersSize: Int,
    question: Board?,
    comment: MutableList<BoardComment>?,
    like: MutableList<BoardLike>?,
    view: MutableList<BoardView>?,
    viewCount: Int,
    createdAt: LocalDateTime,
    updatedAt: LocalDateTime
) : Board(
    id,
    title,
    content,
    writer,
    answers,
    answersSize,
    question,
    comment,
    like,
    view,
    viewCount,
    createdAt,
    updatedAt
) {
}