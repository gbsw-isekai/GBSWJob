package kr.hs.gbsw.gbswjob.company.repository

import kr.hs.gbsw.gbswjob.company.domain.Comment
import org.springframework.data.jpa.repository.JpaRepository

interface CommentRepository : JpaRepository<Comment, Int> {
}