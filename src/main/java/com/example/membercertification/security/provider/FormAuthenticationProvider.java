package com.example.membercertification.security.provider;

import com.example.membercertification.domain.dto.AccountContext;
import com.example.membercertification.security.detail.FormAuthenticationDetails;
import com.example.membercertification.security.exception.SecretException;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component("authenticationProvider") // 스프링 시큐리티에서 인증을 처리하는 인터페이스를 구현한 클래스를 빈으로 등록
//@Primary
@RequiredArgsConstructor // final 필드를 가지는 생성자를 생성
public class FormAuthenticationProvider implements AuthenticationProvider { // FormAuthenticationProvider : 사용자 인증을 처리하는 클래스

    private final UserDetailsService userDetailsService; // 사용자 정보를 조회하는 인터페이스
    private final PasswordEncoder passwordEncoder; // 비밀번호 암호화 인터페이스

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException { // 사용자 인증을 처리하는 메서드

        String loginId = authentication.getName(); // 사용자 이름
        String password = (String) authentication.getCredentials(); // 사용자 비밀번호

        AccountContext accountContext = (AccountContext)userDetailsService.loadUserByUsername(loginId); // 사용자 이름으로 사용자 정보 조회

        if (!passwordEncoder.matches(password, accountContext.getPassword())) { // 사용자 비밀번호와 입력한 비밀번호가 일치하지 않으면 예외 발생
            throw new BadCredentialsException("Invalid password");
        }

        String secretKey = ((FormAuthenticationDetails) authentication.getDetails()).getSecretKey(); // 사용자가 입력한 secretKey 값
        if (secretKey == null || !secretKey.equals("secret")) { // secretKey 값이 null이거나 "secret"이 아니면 예외 발생
            throw new SecretException("Invalid Secret");
        }
        return new UsernamePasswordAuthenticationToken(accountContext.getAccountDTO(), null, accountContext.getAuthorities()); // 사용자 정보를 담은 UsernamePasswordAuthenticationToken 객체 생성
    }

    @Override
    public boolean supports(Class<?> authentication) { // 인증 객체가 UsernamePasswordAuthenticationToken 클래스와 같은지 확인
        return authentication.isAssignableFrom(UsernamePasswordAuthenticationToken.class); // UsernamePasswordAuthenticationToken 클래스와 같으면 true 반환
    }
}
