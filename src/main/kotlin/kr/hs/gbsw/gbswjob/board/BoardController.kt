package kr.hs.gbsw.gbswjob.board

import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class BoardController {
    @GetMapping("/")
    fun getAll(): String {
        val authentication = SecurityContextHolder.getContext().authentication
        val userId = authentication.principal as String

//        if (authentication.authorities.any { it.authority != "READ_BOARD" }) {
//            throw Exception("asdf")
//        }

        return userId
    }
}