package kr.hs.gbsw.gbswjob.company.controller

import kr.hs.gbsw.gbswjob.common.AuthUserId
import kr.hs.gbsw.gbswjob.company.domain.CompanyComment
import kr.hs.gbsw.gbswjob.company.dto.CommentCreateDto
import kr.hs.gbsw.gbswjob.company.dto.CommentGetDto
import kr.hs.gbsw.gbswjob.company.dto.CommentUpdateDto
import kr.hs.gbsw.gbswjob.company.service.CompanyCommentService
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/companies/{companyId}/comments")
class CompanyCommentController (
    private var service: CompanyCommentService
){
    @GetMapping
    fun getComments(
        @AuthUserId userId: String?,
        @PathVariable companyId: Int
    ): List<CommentGetDto> {
        return service.getComments(companyId, userId)
    }

    //댓글 작성
    @PostMapping
    fun create(
        @AuthUserId userId: String,
        @PathVariable("companyId") companyId: Int,
        @RequestBody dto: CommentCreateDto
    ): CompanyComment {
        return service.create(userId, companyId, dto)
    }

    //댓글 업데이트
    @PutMapping("/{id}")
    fun update(
        @AuthUserId userId: String,
        @PathVariable("companyId") companyId: Int,
        @PathVariable("id") commentId: Int,
        @RequestBody dto: CommentUpdateDto,
    ): CompanyComment {
        return service.update(userId, companyId, commentId, dto)
    }

    //댓글 삭제
    @DeleteMapping("/{id}")
    fun delete(
        @AuthUserId userId: String,
        @PathVariable("companyId") companyId: Int,
        @PathVariable("id") commentId: Int
    ) {
        return service.delete(userId, companyId, commentId)
    }
}