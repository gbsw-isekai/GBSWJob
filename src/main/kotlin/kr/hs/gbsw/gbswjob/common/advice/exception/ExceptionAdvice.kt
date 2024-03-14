package kr.hs.gbsw.gbswjob.common.advice.exception

import kr.hs.gbsw.gbswjob.common.response.ErrorResponse
import org.springframework.http.HttpStatus

/**
 * 예외를 처리하는 Advice를 위한 추상 클래스입니다.
 *
 * @author KDY
 */
abstract class ExceptionAdvice {
    protected fun getErrorResponse(status: HttpStatus, exception: Exception): ErrorResponse {
        return getErrorResponse(status, exception.message)
    }

    protected fun getErrorResponse(status: HttpStatus, message: String?): ErrorResponse {
        // TODO: KDY - ErrorResponse statusCode 를 숫자로 받도록?
        return ErrorResponse(status.value(), message ?: "")
    }

    protected fun conflict(message: String): ErrorResponse {
        return getErrorResponse(HttpStatus.CONFLICT, message)
    }

    protected fun badRequest(exception: Exception): ErrorResponse {
        return getErrorResponse(HttpStatus.BAD_REQUEST, exception)
    }

    protected fun badRequest(message: String?): ErrorResponse {
        return getErrorResponse(HttpStatus.BAD_REQUEST, message)
    }

    protected fun unauthorized(message: String): ErrorResponse {
        return getErrorResponse(HttpStatus.UNAUTHORIZED, message)
    }

    protected fun forbidden(message: String): ErrorResponse {
        return getErrorResponse(HttpStatus.FORBIDDEN, message)
    }

    protected fun notFound(message: String?): ErrorResponse {
        return getErrorResponse(HttpStatus.NOT_FOUND, message)
    }

    protected fun internal(exception: Exception): ErrorResponse {
        return ErrorResponse.internal(exception)
    }
}