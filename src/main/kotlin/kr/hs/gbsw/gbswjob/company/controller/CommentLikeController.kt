package kr.hs.gbsw.gbswjob.company.controller

import kr.hs.gbsw.gbswjob.common.AuthUserId
import kr.hs.gbsw.gbswjob.company.domain.CompanyCommentLike
import kr.hs.gbsw.gbswjob.company.service.CompanyCommentLikeService
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/companies/{companyId}/comments/{commentId}/likes")
class CommentLikeController (
    private var service: CompanyCommentLikeService
){
    @GetMapping
    fun likes(
        @PathVariable companyId: Int,
        @PathVariable commentId: Int
    ): List<CompanyCommentLike> {
        return service.getLikes(companyId, commentId)
    }

    @PostMapping("/me")
    fun addLike(
        @AuthUserId userId: String,
        @PathVariable companyId: Int,
        @PathVariable commentId: Int
    ): CompanyCommentLike {
        return service.add(userId, commentId)
    }

    @DeleteMapping("/me")
    fun delete(
        @AuthUserId userId: String,
        @PathVariable companyId: Int,
        @PathVariable commentId: Int
    ) {
        return service.delete(userId, companyId, commentId)
    }
}