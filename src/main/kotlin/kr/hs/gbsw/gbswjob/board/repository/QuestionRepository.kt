package kr.hs.gbsw.gbswjob.board.repository

import kr.hs.gbsw.gbswjob.board.domain.Question
import org.springframework.data.jpa.repository.JpaRepository

interface QuestionRepository : JpaRepository<Question, Int> {
}