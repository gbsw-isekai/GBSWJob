package kr.hs.gbsw.gbswjob.board.dto

import kr.hs.gbsw.gbswjob.user.domain.User

class BoardCreateDto(
        var title: String?,
        var content: String,
        var questionId: Int?
) {

}
