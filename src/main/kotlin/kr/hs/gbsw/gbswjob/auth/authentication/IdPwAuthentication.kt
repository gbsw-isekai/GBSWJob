package kr.hs.gbsw.gbswjob.auth.authentication

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken

class IdPwAuthentication(principal: Any?, credentials: Any?) : UsernamePasswordAuthenticationToken(principal, credentials) {
}