package kr.hs.gbsw.gbswjob.board.repository

import kr.hs.gbsw.gbswjob.board.domain.Board
import org.springframework.data.jpa.repository.JpaRepository

interface BoardRepository: JpaRepository<Board, Int> {
}