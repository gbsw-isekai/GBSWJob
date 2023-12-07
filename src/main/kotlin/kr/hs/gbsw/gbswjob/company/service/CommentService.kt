package kr.hs.gbsw.gbswjob.company.service

import kr.hs.gbsw.gbswjob.common.AuthUserId
import kr.hs.gbsw.gbswjob.company.domain.Comment
import kr.hs.gbsw.gbswjob.company.dto.CommentCreateDto
import kr.hs.gbsw.gbswjob.company.dto.CommentDeleteDto
import kr.hs.gbsw.gbswjob.company.dto.CommentUpdateDto
import kr.hs.gbsw.gbswjob.company.repository.CommentRepository
import kr.hs.gbsw.gbswjob.company.repository.CompanyRepository
import kr.hs.gbsw.gbswjob.user.repository.UserRepository
import org.springframework.stereotype.Service
import java.lang.IllegalArgumentException
import java.time.LocalDateTime

@Service
class CommentService(
    private var commentRepository: CommentRepository,
    private var userRepository: UserRepository,
    private var companyRepository: CompanyRepository
) {
    fun create (userId: String,id: Int, dto: CommentCreateDto):Comment {

        val user =userRepository.findById(userId).orElseThrow {
            IllegalArgumentException("존재하지 않는 사용자입니다.")
        }

        val company = companyRepository.findById(id).orElseThrow{
            IllegalArgumentException("존재하지 않는 회사입니다.")
        }

        val comment = Comment(
            null,
            user,
            company,
            dto.content,
            0,
            LocalDateTime.now(),
            LocalDateTime.now()
        )
        return commentRepository.save(comment)
    }

    fun update (userId: String,id: Int,dto: CommentUpdateDto):Comment {

        val user = userRepository.findById(userId).orElseThrow{
            IllegalArgumentException("존재하지 않는 사용자입니다.")
        }

        val company = companyRepository.findById(id).orElseThrow{
            IllegalArgumentException("존재하지 않는 회사입니다.")
        }

        val updateComment = commentRepository.findById(dto.id).get()

        if(user != updateComment.writer || company != updateComment.company){
            throw IllegalArgumentException("자신의 댓글만 수정할 수 있습니다.")
        }

        updateComment.content = dto.content
        updateComment.updatedAt = LocalDateTime.now()

        return commentRepository.save(updateComment)
    }

    fun delete(userId: String, id: Int, dto: CommentDeleteDto):Boolean {

        val user = userRepository.findById(userId).orElseThrow{
            IllegalArgumentException("존재하지 않는 사용자입니다.")
        }

        val company = companyRepository.findById(id).orElseThrow{
            IllegalArgumentException("존재하지 않는 회사입니다.")
        }

        val comment = commentRepository.findById(dto.id).orElseThrow{
            IllegalArgumentException("존재하지 않는 댓글입니다.")
        }

        if(user != comment.writer || company != comment.company) {
            throw IllegalArgumentException("자신의 댓글만 삭제할 수 있습니다.")
        }

        commentRepository.deleteById(dto.id)

        return true
    }
}