package kr.hs.gbsw.gbswjob.company.service

import kr.hs.gbsw.gbswjob.company.domain.CommentLike
import kr.hs.gbsw.gbswjob.company.repository.CommentLikeRepository
import kr.hs.gbsw.gbswjob.company.repository.CommentRepository
import kr.hs.gbsw.gbswjob.company.repository.CompanyRepository
import org.springframework.stereotype.Service
import java.lang.IllegalArgumentException

@Service
class CommentLikeService (
    private var commentLikeRepository: CommentLikeRepository,
    private var commentRepository: CommentRepository,
    private var companyRepository: CompanyRepository
){
    fun add(commentId: Int): CommentLike {
        val commentLike = commentLikeRepository.findById(commentId).orElseThrow {
            IllegalArgumentException("이미 좋아요를 누르셨습니다.")
        }

        return commentLikeRepository.save(commentLike)
    }

    fun getLikes(companyId: Int, commentId: Int): List<CommentLike> {
        val company = companyRepository.findById(companyId).orElseThrow {
            IllegalArgumentException("좋아요가 추가 된 댓글의 회사를 찾을 수가 없습니다.")
        }

        val comment = commentRepository.findById(commentId).orElseThrow {
            IllegalArgumentException("좋아요가 추가 된 댓글을 찾을 수가 없습니다.")
        }

        return commentLikeRepository.findAll()
    }

    fun delete(commentId: Int) {
        val commentLike = commentLikeRepository.findById(commentId).orElseThrow {
            IllegalArgumentException("이미 좋아요가 취소되었습니다.")
        }

        return commentLikeRepository.deleteById(commentId)
    }
}