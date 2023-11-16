package kr.hs.gbsw.gbswjob.auth.filter

import io.jsonwebtoken.JwtException
import io.jsonwebtoken.Jwts
import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import kr.hs.gbsw.gbswjob.auth.JwtUtils
import org.slf4j.LoggerFactory
import org.slf4j.MDC
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.BadCredentialsException
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.web.filter.OncePerRequestFilter
import java.lang.Exception

class JwtAuthenticationFilter(
        private val authenticationManager: AuthenticationManager,
        private val jwtUtils: JwtUtils
) : OncePerRequestFilter() {
    private val log = LoggerFactory.getLogger(javaClass)

    override fun doFilterInternal(
            request: HttpServletRequest,
            response: HttpServletResponse,
            filterChain: FilterChain
    ) {

        try {
            val authorization = request.getHeader(AUTHORIZATION)
            if (authorization != null) {
                val bearerToken = authorization.replace("Bearer", "").trim()

                // JWT 파싱해서 검증하고 사용자 아이디 가져오기
                val userId = jwtUtils.getUserId(bearerToken)

                SecurityContextHolder.getContext().authentication = UsernamePasswordAuthenticationToken(
                    userId, bearerToken
                )
            }
        } catch (e: Exception) {
            // NOTE: KDY - JWT 오류가 발생했더라도 여기서 return 을 하면 스프링이 응답으로 200 / 데이터 없이 보낸다. return 하지 말것.
            SecurityContextHolder.clearContext()

            if (e !is JwtException || e !is BadCredentialsException) {
                e.printStackTrace()
            }
        }
        filterChain.doFilter(request, response)
        MDC.clear()
    }

    companion object {
        const val AUTHORIZATION = "Authorization"
    }
}