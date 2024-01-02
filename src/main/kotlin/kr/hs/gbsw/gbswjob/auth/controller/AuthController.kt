package kr.hs.gbsw.gbswjob.auth.controller

import kr.hs.gbsw.gbswjob.auth.authentication.dto.LoginDto
import kr.hs.gbsw.gbswjob.auth.service.AuthService
import kr.hs.gbsw.gbswjob.common.AuthUserId
import kr.hs.gbsw.gbswjob.user.dto.UserRegisterDto
import kr.hs.gbsw.gbswjob.user.repository.UserRepository
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/auth")
class AuthController(
    private val service: AuthService,
    private val repository: UserRepository,
) {

    // 회원 가입
    @PostMapping("/join")
    fun register(
        @RequestBody dto: UserRegisterDto,
        @AuthUserId userId: String?
    ): String {
        return service.register(dto, userId)
    }
    @PostMapping("/login")
    fun login(@RequestBody dto: LoginDto): String {
        return service.login(dto)
    }
}