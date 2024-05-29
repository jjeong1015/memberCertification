package com.example.membercertification.security.provider;

import com.example.membercertification.domain.dto.AccountContext;
import com.example.membercertification.security.token.RestAuthenticationToken;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component("restAuthenticationProvider") // Spring Bean으로 등록
@RequiredArgsConstructor // final 필드를 가지는 생성자를 생성
public class RestAuthenticationProvider implements AuthenticationProvider { // RestAuthenticationProvider : 사용자 인증을 처리하는 클래스

    private final UserDetailsService userDetailsService; // 사용자 정보를 가져오기 위한 UserDetailsService
    private final PasswordEncoder passwordEncoder; // 비밀번호 암호화를 위한 PasswordEncoder

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {

        String loginId = authentication.getName(); // 사용자 ID
        String password = (String) authentication.getCredentials(); // 사용자 비밀번호
        AccountContext accountContext = (AccountContext) userDetailsService.loadUserByUsername(loginId); // 사용자 정보를 가져옴

        if(!passwordEncoder.matches(password, accountContext.getPassword())) { // 비밀번호가 일치하지 않으면
            throw new BadCredentialsException("Invalid password");
        }

        return new RestAuthenticationToken(accountContext.getAuthorities(), accountContext.getAccountDTO(), null); // RestAuthenticationToken 생성
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.isAssignableFrom(RestAuthenticationToken.class);
    }
}
