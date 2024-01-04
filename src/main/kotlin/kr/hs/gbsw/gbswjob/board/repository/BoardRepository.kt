package kr.hs.gbsw.gbswjob.board.repository

import kr.hs.gbsw.gbswjob.board.domain.Board
import kr.hs.gbsw.gbswjob.board.projection.BoardQuestionProjection
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query

interface BoardRepository: JpaRepository<Board, Int> {
    fun findByQuestionIdIsNull(): List<BoardQuestionProjection>
    fun findByQuestionIdIsNullOrderByAnswersSizeDesc(): List<BoardQuestionProjection>
    fun findByQuestionIdIsNullOrderByCreatedAtDesc(): List<BoardQuestionProjection>
    fun findByQuestionIdIsNullOrderByViewCountDesc(): List<BoardQuestionProjection>
}
