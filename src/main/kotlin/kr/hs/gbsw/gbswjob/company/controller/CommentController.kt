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
@RequestMapping("/companies/{id}")
class CommentController (
    private var service: CommentService
){
    @PostMapping("/comment")
    fun create(@AuthUserId userId: String, @PathVariable("id") id:Int,@RequestBody dto: CommentCreateDto): Comment {
        return service.create(userId, id, dto)
    }

    @PutMapping("comment")
    fun update(@AuthUserId userId: String,  @PathVariable("id") id:Int, @RequestBody dto: CommentUpdateDto): Comment {
        return service.update(userId, id, dto)
    }
    @DeleteMapping("comment")
    fun delete(@AuthUserId userId: String, @PathVariable("id") id:Int, @RequestBody dto: CommentDeleteDto) : Boolean {
        return service.delete(userId, id, dto)
    }
}