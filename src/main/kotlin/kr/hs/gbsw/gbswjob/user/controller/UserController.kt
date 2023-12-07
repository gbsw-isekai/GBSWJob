package kr.hs.gbsw.gbswjob.user.controller

import kr.hs.gbsw.gbswjob.user.domain.User
import kr.hs.gbsw.gbswjob.user.dto.UserRegisterDto
import kr.hs.gbsw.gbswjob.user.service.UserService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/users")
class UserController(
        private val service: UserService
) {
    @PostMapping
    fun register(@RequestBody dto: UserRegisterDto): User {
        return service.register(dto.id, dto.pw)
    }

    @GetMapping("/{id}")
    fun getUser(@PathVariable id:String): User {
        return service.getUser(id)
    }
}