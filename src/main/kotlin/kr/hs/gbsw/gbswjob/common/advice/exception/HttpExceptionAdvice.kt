package kr.hs.gbsw.gbswjob.common.advice.exception

import kr.hs.gbsw.gbswjob.common.response.ErrorResponse
import org.springframework.core.Ordered
import org.springframework.core.annotation.Order
import org.springframework.data.mapping.PropertyReferenceException
import org.springframework.http.HttpStatus
import org.springframework.http.converter.HttpMessageNotReadableException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.bind.annotation.RestControllerAdvice
import java.lang.UnsupportedOperationException
import jakarta.persistence.EntityExistsException
import jakarta.persistence.EntityNotFoundException
import kr.hs.gbsw.gbswjob.common.excpetion.UnauthorizedException

/**
 * Http 예외와 관련된 처리를 하는 Advice 입니다.
 *
 * @author KDY
 */
@RestControllerAdvice(annotations = [RestController::class]) //restController 지정 (요 로직을 거쳐라~)
@Order(Ordered.HIGHEST_PRECEDENCE)
class HttpExceptionAdvice : ExceptionAdvice() {
    @ExceptionHandler(value = [ IllegalArgumentException::class, IllegalStateException::class, UnsupportedOperationException::class ])
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    fun handleBadRequestException(exception: Exception): ErrorResponse {
        return ErrorResponse(HttpStatus.BAD_REQUEST.value(), exception.message ?: "")
    }

    @ExceptionHandler(value = [UnauthorizedException::class])
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    fun handleUnauthorizedException(exception: UnauthorizedException): ErrorResponse {
        return ErrorResponse.unauthorized(exception.message!!)
    }

    @ExceptionHandler(value = [ EntityExistsException::class ])
    @ResponseStatus(HttpStatus.CONFLICT)
    fun handleResourceExistsException(exception: Exception): ErrorResponse {
        return conflict(exception.message ?: "리소스가 이미 존재합니다.")
    }

    @ExceptionHandler(EntityNotFoundException::class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    fun handleResourceNotFoundException(exception: EntityNotFoundException): ErrorResponse {
        return notFound("요청한 ${exception.message ?: "리소스"}이(가) 존재하지 않습니다.")
    }

    @ExceptionHandler(HttpMessageNotReadableException::class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    fun handleHttpMessageNotReadableException(exception: HttpMessageNotReadableException?): ErrorResponse {
        return badRequest(
            "해당 요청의 메세지를 읽을 수 없습니다. 요청 내용이 비워져있거나 형식에 맞지 않은지 확인해주세요."
        )
    }

    @ExceptionHandler(PropertyReferenceException::class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    fun handlePropertyReferenceException(exception: PropertyReferenceException): ErrorResponse {
        return badRequest(String.format("%s 필드가 존재하지 않습니다.", exception.propertyName))
    }

    @ExceptionHandler(SecurityException::class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    fun handleSecurityException(exception: SecurityException): ErrorResponse {
        return forbidden(exception.message ?: "요청하신 동작을 수행하기 위해서는 권한이 필요합니다.")
    }
}