package kr.hs.gbsw.gbswjob.common.response

import com.fasterxml.jackson.annotation.JsonFormat
import java.sql.Timestamp

/**
 * 기본 Response 추상클래스입니다.
 *
 * @author STK
 */
abstract class BasicResponse {
    val status: Int
    val message: String

    @JsonFormat(shape = JsonFormat.Shape.STRING)
    val timestamp: Timestamp

    constructor(status: Int, message: String) {
        this.status = status
        this.message = message
        timestamp = Timestamp(System.currentTimeMillis())
    }

    /**
     * 생성자입니다.
     *
     * @param status 상태 코드
     * @param message 메세지
     * @param timestamp 요청 일시
     */
    constructor(status: Int, message: String, timestamp: Timestamp) {
        this.status = status
        this.message = message
        this.timestamp = timestamp
    }
}