package kr.hs.gbsw.gbswjob.company.service

import jakarta.persistence.Id
import kr.hs.gbsw.gbswjob.company.domain.CommentLike
import kr.hs.gbsw.gbswjob.company.domain.Company
import kr.hs.gbsw.gbswjob.company.dto.CommentLikeDto
import kr.hs.gbsw.gbswjob.company.repository.CommentLikeRepository
import kr.hs.gbsw.gbswjob.company.repository.CompanyRepository
import kr.hs.gbsw.gbswjob.user.domain.User
import org.springframework.stereotype.Service
import java.lang.IllegalArgumentException

@Service
class CommentLikeService (
    private var commentLikeRepository: CommentLikeRepository,
){
    fun add(dto: CommentLikeDto, id: Int): CommentLike {
        val commentLike = commentLikeRepository.findById(id).orElseThrow {
            IllegalArgumentException("이미 좋아요를 누르셨습니다.")
        }

        return commentLikeRepository.save(commentLike)
    }

    fun getLikes(): List<CommentLike> {
        return commentLikeRepository.findAll()
    }

    fun delete(like: Int) {
        val commentLike = commentLikeRepository.findById(like).orElseThrow {
            IllegalArgumentException("이미 좋아요가 취소되었습니다.")
        }

        return commentLikeRepository.deleteById(like)
    }
}