package kr.hs.gbsw.gbswjob.company.dto

import kr.hs.gbsw.gbswjob.company.domain.CompanyCommentLike
import kr.hs.gbsw.gbswjob.user.domain.User
import java.time.LocalDateTime

class CommentGetDto(
    var id: Int,
    var writer: User,
    var content: String,
    var likes: Int,     // 총 좋아요 수
    var liked: Boolean, // 내가 좋아요 했는지 유무
    var createdAt: LocalDateTime,
    var updatedAt: LocalDateTime
) {
}