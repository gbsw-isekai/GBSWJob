package kr.hs.gbsw.gbswjob.company.repository

import kr.hs.gbsw.gbswjob.company.domain.CompanyComment
import kr.hs.gbsw.gbswjob.company.domain.CompanyCommentLike
import kr.hs.gbsw.gbswjob.user.domain.User
import org.springframework.data.jpa.repository.JpaRepository
import java.util.Optional

interface CompanyCommentLikeRepository: JpaRepository<CompanyCommentLike, Int> {
    fun findByUserAndComment(user: User, comment: CompanyComment): Optional<CompanyCommentLike>
    fun existsByUser(user: User): Boolean
}