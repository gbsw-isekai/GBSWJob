package kr.hs.gbsw.gbswjob.auth.controller

import kr.hs.gbsw.gbswjob.auth.authentication.dto.LoginDto
import kr.hs.gbsw.gbswjob.auth.authentication.service.AuthService
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/auth")
class AuthController(
        private val service: AuthService
) {
    @PostMapping("/login")
    fun login(@RequestBody dto: LoginDto): String {
        return service.login(dto.id, dto.pw)
    }
}