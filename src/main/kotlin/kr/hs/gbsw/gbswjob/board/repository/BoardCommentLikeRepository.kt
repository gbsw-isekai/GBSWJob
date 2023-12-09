package kr.hs.gbsw.gbswjob.board.repository

import kr.hs.gbsw.gbswjob.board.domain.BoardComment
import kr.hs.gbsw.gbswjob.board.domain.BoardCommentLike
import kr.hs.gbsw.gbswjob.user.domain.User
import org.springframework.data.jpa.repository.JpaRepository

interface BoardCommentLikeRepository: JpaRepository<BoardCommentLike, Int> {
    fun existsByUserAndComment(user: User, comment: BoardComment): Boolean
    fun getByUserAndComment(user: User, comment: BoardComment): BoardCommentLike
}