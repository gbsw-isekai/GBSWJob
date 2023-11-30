package kr.hs.gbsw.gbswjob.auth.authentication

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.GrantedAuthority

class IdPwAuthentication(principal: Any?, credentials: Any?, authorities: List<GrantedAuthority>) : UsernamePasswordAuthenticationToken(principal, credentials, authorities) {

}

//class IdPwAuthentication extedns UserName.... {
//    Object principal;
//    Object credentials;
//
//    IdPwAuthentication(prinicpal, crednetnials) {
//        super(principal, crede, authorities)
//    }
//}