package kr.hs.gbsw.gbswjob.auth.controller

import kr.hs.gbsw.gbswjob.auth.authentication.dto.LoginDto
import kr.hs.gbsw.gbswjob.auth.service.AuthService
import kr.hs.gbsw.gbswjob.common.AuthUserId
import kr.hs.gbsw.gbswjob.common.response.CommonResponse
import kr.hs.gbsw.gbswjob.common.response.CommonResponse.Companion.ok
import kr.hs.gbsw.gbswjob.user.dto.UserRegisterDto
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/auth")
class AuthController(
    private val service: AuthService,
) {
    // 회원 가입
    @PostMapping("/join")
    fun register(
        @RequestBody dto: UserRegisterDto,
        @AuthUserId userId: String?
    ): CommonResponse<String> {
        /*
        나중에는 Access Token, Refresh Token 을 돌려줘야 겠음.
        왜냐면 지금 발급되는 토큰은 DB에 저장이 안되는거라 보안적으로 취약함.
        있으면 그냥 접근가능한데, 털렸을 때 없앨 방법이 없음.

        그래서 짧은 만료 시간을 가지는 Access Token 과
        긴 만료 시간을 가지고 DB에 저장하는 Refresh Token을 만듬
         */
        return ok(service.register(dto, userId))
    }

    @PostMapping("/login")
    fun login(@RequestBody dto: LoginDto): CommonResponse<String> {
        return ok(service.login(dto))
    }

//    @PostMapping("/refresh")
//    fun refresh(@RequestBody dto: RefreshDto): String {
//        return service.refresh(dto)
//    }
}