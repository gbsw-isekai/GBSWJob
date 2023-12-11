package kr.hs.gbsw.gbswjob.user.controller

import kr.hs.gbsw.gbswjob.common.AuthUserId
import kr.hs.gbsw.gbswjob.user.domain.User
import kr.hs.gbsw.gbswjob.user.dto.UserRegisterDto
import kr.hs.gbsw.gbswjob.user.dto.UserUpdateDto
import kr.hs.gbsw.gbswjob.user.service.UserService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/users")
class UserController(
        private val service: UserService
) {
    // 회원 가입
    @PostMapping("/join")
    fun register(
        @RequestBody dto: UserRegisterDto,
        @AuthUserId userId: String
    ): User {
        return service.register(userId, dto.id, dto.pw, dto.name, dto.number, dto.profile)
    }
//개인 프로필
    @GetMapping("/me")
    fun getMyAccount(@AuthUserId userId: String): User {
        return service.getUser(userId)
    }
//개인 정보 수정
    @PutMapping("/me/edit")
    fun userUpdate(
        @RequestBody dto: UserUpdateDto,
        @AuthUserId userId: String
    ): User {
        return service.updateUser(dto, userId)
    }


    //test를 위한 유저 조회
    @GetMapping
    fun users(): List<User> {
        return service.getUsers()
    }
}