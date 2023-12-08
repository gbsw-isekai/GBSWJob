package kr.hs.gbsw.gbswjob.board.controller

import kr.hs.gbsw.gbswjob.board.domain.Board
import kr.hs.gbsw.gbswjob.board.domain.Comment
import kr.hs.gbsw.gbswjob.board.dto.BoardCreateDto
import kr.hs.gbsw.gbswjob.board.dto.BoardUpdateDto
import kr.hs.gbsw.gbswjob.board.dto.CommentCreateDto
import kr.hs.gbsw.gbswjob.board.dto.CommentUpdateDto
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

    @PostMapping("/{id}/comments")
    fun createComment(@AuthUserId userId: String, @PathVariable("id") id: Int, @RequestBody dto: CommentCreateDto): Comment {
        return service.createComment(userId, id, dto)
    }

    @PutMapping("/comments")
    fun updateComment(@AuthUserId userId: String, @RequestBody dto: CommentUpdateDto): Comment {
        return service.updateComment(userId, dto)
    }

    @DeleteMapping("/{boardId}/comments/{commentId}")
    fun deleteComment(@AuthUserId userId: String, @PathVariable("boardId") boardId: Int, @PathVariable("commentId") commentId: Int) {
        return service.deleteComment(userId, boardId, commentId)
    }
}