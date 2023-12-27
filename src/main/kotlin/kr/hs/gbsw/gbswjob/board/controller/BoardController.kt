package kr.hs.gbsw.gbswjob.board.controller

import kr.hs.gbsw.gbswjob.board.domain.Board
import kr.hs.gbsw.gbswjob.board.domain.BoardLike
import kr.hs.gbsw.gbswjob.board.dto.BoardCreateDto
import kr.hs.gbsw.gbswjob.board.projection.BoardQuestionProjection
import kr.hs.gbsw.gbswjob.board.dto.BoardUpdateDto
import kr.hs.gbsw.gbswjob.board.service.BoardService
import kr.hs.gbsw.gbswjob.common.AuthUserId
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/boards")
class BoardController(
        private val service: BoardService
){
    @GetMapping
    fun getAll(): List<Board> {
        return service.getAll();
    }

    @GetMapping("/questions")
    fun getQuestions(): List<BoardQuestionProjection> {
        return service.getQuestions();
    }

    @GetMapping("/{id}")
    fun getById(@PathVariable("id") id: Int): Board {
        return service.getById(id);
    }

    @PostMapping
    fun create(@AuthUserId userId: String, @RequestBody dto: BoardCreateDto): Board {
        return service.create(userId, dto)
    }

    @PutMapping
    fun update(@AuthUserId userId: String, @RequestBody dto: BoardUpdateDto): Board {
        return service.update(userId, dto);
    }

    @DeleteMapping("/{id}")
    fun delete(@AuthUserId userId: String, @PathVariable("id") id: Int) {
        return service.delete(userId, id);
    }

    @PostMapping("/{boardId}/likes/me")
    fun createLike(@AuthUserId userId: String, @PathVariable("boardId") boardId: Int): BoardLike {
        return service.createLike(userId, boardId)
    }

    @DeleteMapping("/{boardId}/likes/me")
    fun deleteLike(@AuthUserId userId: String, @PathVariable("boardId") boardId: Int) {
        return service.deleteLike(userId, boardId)
    }

    @PostMapping("/{boardId}/views")
    fun createView(@AuthUserId userId: String?, @PathVariable("boardId") boardId: Int) {
        return service.createView(userId, boardId)
    }
}