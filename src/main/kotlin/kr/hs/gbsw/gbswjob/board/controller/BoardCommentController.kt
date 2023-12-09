package kr.hs.gbsw.gbswjob.board.controller

import kr.hs.gbsw.gbswjob.board.domain.BoardComment
import kr.hs.gbsw.gbswjob.board.domain.BoardCommentLike
import kr.hs.gbsw.gbswjob.board.dto.BoardCommentCreateDto
import kr.hs.gbsw.gbswjob.board.dto.BoardCommentUpdateDto
import kr.hs.gbsw.gbswjob.board.service.BoardCommentService
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
@RequestMapping("/boards/{boardId}/comments")
class BoardCommentController(
        private val service: BoardCommentService
){

    @GetMapping()
    fun getByBoardId(@PathVariable("boardId") boardId: Int): List<BoardComment> {
        return service.getByBoardId(boardId)
    }

    @PostMapping()
    fun create(@AuthUserId userId: String, @PathVariable("boardId") boardId: Int, @RequestBody dto: BoardCommentCreateDto): BoardComment {
        return service.create(userId, boardId, dto)
    }

    @PutMapping("/{commentId}")
    fun update(@AuthUserId userId: String, @PathVariable("boardId") boardId: Int, @PathVariable("commentId") commentId: Int, @RequestBody dto: BoardCommentUpdateDto): BoardComment {
        return service.update(userId, boardId, commentId, dto)
    }

    @DeleteMapping("/{commentId}")
    fun delete(@AuthUserId userId: String, @PathVariable("boardId") boardId: Int, @PathVariable("commentId") commentId: Int) {
        return service.delete(userId, boardId, commentId)
    }

    @PostMapping("/{commentId}/likes/me")
    fun createLike(@AuthUserId userId: String, @PathVariable("boardId") boardId: Int, @PathVariable("commentId") commentId: Int): BoardCommentLike {
        return service.createLike(userId, boardId, commentId)
    }

    @DeleteMapping("/{commentId}/likes/me")
    fun deleteLike(@AuthUserId userId: String, @PathVariable("boardId") boardId: Int, @PathVariable("commentId") commentId: Int) {
        return service.deleteLike(userId, boardId, commentId)
    }
}