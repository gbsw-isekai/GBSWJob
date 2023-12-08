package kr.hs.gbsw.gbswjob.company.repository

import kr.hs.gbsw.gbswjob.company.domain.CommentLike
import org.springframework.data.jpa.repository.JpaRepository

interface CommentLikeRepository: JpaRepository<CommentLike, Int> {
}