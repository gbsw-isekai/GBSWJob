package kr.hs.gbsw.gbswjob.auth.authentication

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.GrantedAuthority

class IdPwAuthentication : UsernamePasswordAuthenticationToken {
    constructor(principal: Any?, credentials: Any?) : super(principal, credentials)
    constructor(principal: Any?, credentials: Any?, authorities: Collection<GrantedAuthority>?) : super(
        principal,
        credentials,
        authorities
    )
}

//class IdPwAuthentication extedns UserName.... {
//    Object principal;
//    Object credentials;
//
//    IdPwAuthentication(prinicpal, crednetnials) {
//        super(principal, crede, authorities)
//    }
//}