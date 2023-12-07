package kr.hs.gbsw.gbswjob.company.dto

import kr.hs.gbsw.gbswjob.company.domain.Comment
import kr.hs.gbsw.gbswjob.user.domain.User
import org.springframework.data.annotation.Id

class CommentLikeDto (
    var id: Id,
    var user: User,
    var comment: Comment
){

}