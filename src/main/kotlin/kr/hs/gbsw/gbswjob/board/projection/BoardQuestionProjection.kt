package kr.hs.gbsw.gbswjob.board.projection

import kr.hs.gbsw.gbswjob.board.domain.Board
import kr.hs.gbsw.gbswjob.board.domain.BoardLike
import kr.hs.gbsw.gbswjob.user.domain.User
import java.time.LocalDateTime

interface BoardQuestionProjection {
    var id: Int
    var title: String
    var content: String
    var writer: User
    var answers: MutableList<Board>?
    var like: MutableList<BoardLike>?
    var viewCount: Int
    var createdAt: LocalDateTime
    var updatedAt: LocalDateTime
}