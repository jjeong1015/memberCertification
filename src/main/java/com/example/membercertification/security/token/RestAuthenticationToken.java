package com.example.membercertification.security.token;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

public class RestAuthenticationToken extends AbstractAuthenticationToken { // RestAuthenticationToken : 사용자 인증을 위한 토큰

    private final Object principal; // principal : 사용자 정보
    private final Object credentials; // credentials : 사용자 비밀번호

    public RestAuthenticationToken(Collection<? extends GrantedAuthority> authorities, Object principal, Object credentials) {
        super(authorities); // 부모 클래스의 생성자 호출, 호출 이유 : AbstractAuthenticationToken의 생성자를 호출하여 authorities를 설정
        this.principal = principal;
        this.credentials = credentials;
        setAuthenticated(true); // 인증된 상태로 설정
    }

    public RestAuthenticationToken(Object principal, Object credentials) {
        super(null);
        this.principal = principal;
        this.credentials = credentials;
        setAuthenticated(false); // 인증되지 않은 상태로 설정
    }

    @Override
    public Object getPrincipal() {
        return this.principal; // principal을 반환
    }

    @Override
    public Object getCredentials() {
        return this.credentials; // credentials를 반환
    }
}
