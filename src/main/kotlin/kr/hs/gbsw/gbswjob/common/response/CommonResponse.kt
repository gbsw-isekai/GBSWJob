package kr.hs.gbsw.gbswjob.common.response

/**
 * 일반적인 Response 클래스입니다.
 *
 * @param <T> Response에 들어갈 data의 타입
 * @author STK
</T> */
class CommonResponse<T>(status: Int, message: String?, val data: T) : BasicResponse(status, message!!) {
    constructor(data: T) : this(200, "요청에 성공하였습니다.", data)

    companion object {
        fun <T> ok(data: T): CommonResponse<T> {
            return CommonResponse(data)
        }

        fun ok(): CommonResponse<String> {
            return CommonResponse("")
        }
    }
}