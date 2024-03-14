package kr.hs.gbsw.gbswjob.company.dto

import kr.hs.gbsw.gbswjob.company.domain.CompanyCommentLike
import kr.hs.gbsw.gbswjob.user.domain.User
import java.time.LocalDateTime

class CommentGetDto(
    var id: Int,
    var writer: User,
    var content: String,
    var createdAt: LocalDateTime,
    var updatedAt: LocalDateTime
) {
}