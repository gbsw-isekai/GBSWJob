package kr.hs.gbsw.gbswjob.board.repository

import kr.hs.gbsw.gbswjob.board.domain.Board
import kr.hs.gbsw.gbswjob.board.projection.BoardQuestionProjection
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query

interface BoardRepository: JpaRepository<Board, Int> {
    fun findByQuestionIdIsNull(): List<BoardQuestionProjection>
    fun findByQuestionIdIsNullOrderByAnswersSizeDesc(): List<BoardQuestionProjection>
    fun findByQuestionIdIsNullOrderByCreatedAtDesc(): List<BoardQuestionProjection>
    fun findByQuestionIdIsNullOrderByViewCountDesc(): List<BoardQuestionProjection>

    @Query("select b from Board b where b.question = null")
    fun findQuestions(pageable: Pageable): Page<BoardQuestionProjection>
}
