package kr.hs.gbsw.gbswjob.company.controller

import kr.hs.gbsw.gbswjob.common.AuthUserId
import kr.hs.gbsw.gbswjob.company.domain.Comment
import kr.hs.gbsw.gbswjob.company.dto.CommentCreateDto
import kr.hs.gbsw.gbswjob.company.dto.CommentDeleteDto
import kr.hs.gbsw.gbswjob.company.dto.CommentUpdateDto
import kr.hs.gbsw.gbswjob.company.service.CommentService
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/companies/{company}/comments")
class CommentController (
    private var service: CommentService
){
    @PostMapping
    fun create(@AuthUserId userId: String,
               @PathVariable("company") id: Int,
               @RequestBody dto: CommentCreateDto): Comment {

        return service.create(userId, id, dto)
    }

    @PutMapping("/{id}")
    fun update(@AuthUserId userId: String,
               @PathVariable("company") companyId: Int,
               @PathVariable("id") commentId: Int,
               @RequestBody dto: CommentUpdateDto,): Comment {

        return service.update(userId, companyId,commentId, dto)
    }
    @DeleteMapping("/{id}")
    fun delete(@AuthUserId userId: String,
               @PathVariable("company") companyId: Int,
               @PathVariable("id") commentId: Int) {

        return service.delete(userId, companyId, commentId)
    }
}