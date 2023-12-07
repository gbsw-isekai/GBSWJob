package kr.hs.gbsw.gbswjob.company.controller

import kr.hs.gbsw.gbswjob.company.domain.CommentLike
import kr.hs.gbsw.gbswjob.company.dto.CommentLikeDto
import kr.hs.gbsw.gbswjob.company.service.CommentLikeService
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/companies/comments/likes")
class CommentLikeController (
    private var service: CommentLikeService
){
    @GetMapping
    fun likes():List<CommentLike> {
        return service.getLikes()
    }

    @PostMapping("/{like}")
    fun addLike(@RequestBody dto: CommentLikeDto,
                @PathVariable like: Int,
    ): CommentLike{

        return service.add(dto, like)
    }

    @DeleteMapping("/{like}")
    fun delete(@PathVariable like: Int) {
        return service.delete(like)
    }
}