package kr.hs.gbsw.gbswjob.board.repository

import kr.hs.gbsw.gbswjob.board.domain.Board
import kr.hs.gbsw.gbswjob.board.domain.BoardLike
import kr.hs.gbsw.gbswjob.user.domain.User
import org.springframework.data.jpa.repository.JpaRepository

interface BoardLikeRepository: JpaRepository<BoardLike, Int> {
    fun existsByUserAndBoard(user: User, board: Board): Boolean
    fun getByUserAndBoard(user: User, board: Board): BoardLike
}