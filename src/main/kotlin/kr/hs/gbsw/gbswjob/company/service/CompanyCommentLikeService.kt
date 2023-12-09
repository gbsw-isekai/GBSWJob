package kr.hs.gbsw.gbswjob.company.service

import kr.hs.gbsw.gbswjob.company.domain.CompanyCommentLike
import kr.hs.gbsw.gbswjob.company.repository.CompanyCommentLikeRepository
import kr.hs.gbsw.gbswjob.company.repository.CompanyCommentRepository
import kr.hs.gbsw.gbswjob.company.repository.CompanyRepository
import kr.hs.gbsw.gbswjob.user.repository.UserRepository
import org.springframework.stereotype.Service
import java.lang.IllegalStateException
import kotlin.IllegalArgumentException

@Service
class CompanyCommentLikeService (
    private var companyCommentLikeRepository: CompanyCommentLikeRepository,
    private var companyCommentRepository: CompanyCommentRepository,
    private var companyRepository: CompanyRepository,
    private var userRepository: UserRepository
){
    //댓글 좋아요 추가
    fun add(userId: String, commentId: Int): CompanyCommentLike {
        val comment = companyCommentRepository.findById(commentId).orElseThrow {
            IllegalArgumentException("존재하지 않는 댓글입니다.")
        }

        val user = userRepository.findById(userId).orElseThrow {
            IllegalArgumentException("존재하지 않는 사용자입니다.")
        }

        if (companyCommentLikeRepository.existsByUser(user)) {
            throw IllegalArgumentException("이미 좋아요를 눌렀습니다.")
        }

        val commentLike = CompanyCommentLike(
            null,
            user,
            comment
        )

        return companyCommentLikeRepository.save(commentLike)
    }
    //댓글 조회
    fun getLikes(companyId: Int, commentId: Int): List<CompanyCommentLike> {
        val company = companyRepository.findById(companyId).orElseThrow {
            IllegalArgumentException("존재하지 않는 회사입니다.")
        }

        val comment = companyCommentRepository.findById(commentId).orElseThrow {
            IllegalArgumentException("댓글을 찾을 수 없습니다.")
        }

        return companyCommentLikeRepository.findAll()
    }

    fun delete(userId: String, companyId: Int, commentId: Int) {
        val user = userRepository.findById(userId).orElseThrow {
            IllegalArgumentException("존재하지 않는 유저입니다.")
        }

        val company = companyRepository.findById(companyId).orElseThrow {
            IllegalArgumentException("존재하지 않는 회사입니다.")
        }

        val comment = companyCommentRepository.findById(commentId).orElseThrow {
            IllegalArgumentException("존재하지 않는 댓글입니다.")
        }

        val commentLike = companyCommentLikeRepository.findByUserAndComment(user, comment).orElseThrow {
            IllegalStateException("좋아요 기록이 존재하지 않습니다.")
        }

        return companyCommentLikeRepository.delete(commentLike)
    }
}