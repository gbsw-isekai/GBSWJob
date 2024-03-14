package kr.hs.gbsw.gbswjob.common.response

class ValidationErrorResponse(val violations: List<Violation>) : ErrorResponse(VALIDATE_EXCEPTION) {
    companion object {
        private const val VALIDATE_EXCEPTION = "유효성 검증에 실패하였습니다."
    }
}