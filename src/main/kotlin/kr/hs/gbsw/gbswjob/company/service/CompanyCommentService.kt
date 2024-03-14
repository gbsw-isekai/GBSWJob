package kr.hs.gbsw.gbswjob.company.service

import kr.hs.gbsw.gbswjob.company.domain.CompanyComment
import kr.hs.gbsw.gbswjob.company.dto.CommentCreateDto
import kr.hs.gbsw.gbswjob.company.dto.CommentGetDto
import kr.hs.gbsw.gbswjob.company.dto.CommentUpdateDto
import kr.hs.gbsw.gbswjob.company.repository.CompanyCommentLikeRepository
import kr.hs.gbsw.gbswjob.company.repository.CompanyCommentRepository
import kr.hs.gbsw.gbswjob.company.repository.CompanyRepository
import kr.hs.gbsw.gbswjob.user.repository.UserRepository
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service
import java.time.LocalDateTime

@Service
class CompanyCommentService(
    private var companyCommentRepository: CompanyCommentRepository,
    private var userRepository: UserRepository,
    private var companyRepository: CompanyRepository,
    private var companyCommentLikeRepository: CompanyCommentLikeRepository
) {
    fun getComments(companyId: Int, userId: String?): List<CommentGetDto> {
        val company = companyRepository.findById(companyId).orElseThrow {
            IllegalArgumentException("존재하지 않는 회사입니다.")
        }

        return companyCommentRepository.findByCompany(company).map {
            CommentGetDto(
                it.id!!,
                it.writer,
                it.content,
                it.createdAt,
                it.updatedAt
            )
        }
    }

    fun create (userId: String, companyId: Int, dto: CommentCreateDto): CompanyComment {

        val user = userRepository.findById(userId).orElseThrow {
            IllegalArgumentException("존재하지 않는 사용자입니다.")
        }

        val company = companyRepository.findById(companyId).orElseThrow {
            IllegalArgumentException("존재하지 않는 회사입니다.")
        }
//
//        val like = companyCommentLikeRepository.findAll()

        val comment = CompanyComment(
            null,
            user,
            company,
            dto.content,
            LocalDateTime.now(),
            LocalDateTime.now()
        )
        return companyCommentRepository.save(comment)
    }

    fun update(userId: String, companyId: Int, commentId: Int, dto: CommentUpdateDto): ResponseEntity<String> {

        val user = userRepository.findById(userId).orElseThrow {
            IllegalArgumentException("존재하지 않는 사용자입니다.")
        }

        val company = companyRepository.findById(companyId).orElseThrow {
            IllegalArgumentException("존재하지 않는 회사입니다.")
        }

        val updateComment = companyCommentRepository.findById(commentId).orElseThrow {
            IllegalArgumentException("존재하지 않는 댓글입니다.")
        }

        if(user != updateComment.writer || company != updateComment.company){
            throw IllegalArgumentException("자신의 댓글만 수정할 수 있습니다.")
        }

        updateComment.content = dto.content
        updateComment.updatedAt = LocalDateTime.now()

        companyCommentRepository.save(updateComment)
        
        return ResponseEntity.ok("수정 완료")
    }

    fun delete(userId: String, companyId: Int, commentId: Int) {

        val user = userRepository.findById(userId).orElseThrow {
            IllegalArgumentException("존재하지 않는 사용자입니다.")
        }

        val company = companyRepository.findById(companyId).orElseThrow {
            IllegalArgumentException("존재하지 않는 회사입니다.")
        }

        val comment = companyCommentRepository.findById(commentId).orElseThrow {
            IllegalArgumentException("존재하지 않는 댓글입니다.")
        }

        if(user != comment.writer || company != comment.company) {
            throw IllegalArgumentException("자신의 댓글만 삭제할 수 있습니다.")
        }

        companyCommentRepository.deleteById(commentId)

        return
    }
}