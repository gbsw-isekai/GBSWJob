package kr.hs.gbsw.gbswjob.board.repository

import kr.hs.gbsw.gbswjob.board.domain.BoardView
import org.springframework.data.jpa.repository.JpaRepository

interface BoardViewRepository: JpaRepository<BoardView, Int> {
}