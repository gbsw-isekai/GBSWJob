package kr.hs.gbsw.gbswjob.common.response

/**
 * 에러 Response 클래스입니다.
 *
 * @author STK
 */
open class ErrorResponse(status: Int, message: String) : BasicResponse(status, message) {
    constructor(message: String) : this(400, message) {}

    companion object {
        const val CONTACT_TO_ADMIN = "서버오류가 발생하였습니다. 관리자에게 문의해주세요.\n오류 세부사항: "
        fun badRequest(message: String): ErrorResponse {
            return ErrorResponse(400, message)
        }

        fun unauthorized(message: String): ErrorResponse {
            return ErrorResponse(401, message)
        }

        fun internal(exception: Exception): ErrorResponse {
            return internal(CONTACT_TO_ADMIN, exception)
        }

        fun internal(message: String, exception: Exception): ErrorResponse {
            return ErrorResponse(
                500, """
     $message
     ${exception.message}
     """.trimIndent()
            )
        }
    }
}