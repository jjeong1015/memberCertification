package com.example.membercertification.security.filter;

import com.example.membercertification.domain.dto.AccountDTO;
import com.example.membercertification.security.token.RestAuthenticationToken;
import com.example.membercertification.util.WebUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.context.DelegatingSecurityContextRepository;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.security.web.context.RequestAttributeSecurityContextRepository;
import org.springframework.security.web.context.SecurityContextRepository;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.util.StringUtils;

import java.io.IOException;

public class RestAuthenticationFilter extends AbstractAuthenticationProcessingFilter {

    private final ObjectMapper objectMapper = new ObjectMapper();

    public RestAuthenticationFilter() { // Dsl에서 HttpSecurity http 제거
        super(new AntPathRequestMatcher("/api/login", "POST")); // /api/login으로 요청이 오면 POST 방식으로 처리
        //setSecurityContextRepository(getSecurityContextRepository(http)); // 세션에 저장할 수 있는 SecurityContextRepository 클래스를 가지고 와서 설정 (dsl에서 제거)
    }

    public SecurityContextRepository getSecurityContextRepository(HttpSecurity http) { // Rest 인증 상태 영속하기(세션으로 로그인 유지)
        SecurityContextRepository securityContextRepository = http.getSharedObject(SecurityContextRepository.class); // // http를 통해 현재 객체가 SecurityContextRepository 객체를 가지고 있는지 확인
        if (securityContextRepository == null) { // 객체가 없을 경우
            securityContextRepository = new DelegatingSecurityContextRepository( // DelegatingSecurityContextRepository 생성
                    new RequestAttributeSecurityContextRepository(), new HttpSessionSecurityContextRepository()); // RequestAttributeSecurityContextRepository(요청 범위 : 영속성 보장 X), HttpSessionSecurityContextRepository(세션 범위 : 영속성 보장 O) 추가
        }
        return securityContextRepository; // SecurityContextRepository 반환
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException, IOException {
        if (!HttpMethod.POST.name().equals(request.getMethod()) || !WebUtil.isAjax(request)) { // POST 방식이 아니거나 Ajax 요청이 아니면
            throw new IllegalArgumentException("Authentication method not supported"); // POST 방식이 아니거나 Ajax 요청이 아니면 예외 발생
        }
        AccountDTO accountDTO = objectMapper.readValue(request.getReader(), AccountDTO.class); // AccountDTO로 변환
        if (!StringUtils.hasText(accountDTO.getUsername()) || !StringUtils.hasText(accountDTO.getPassword())) { // username, password가 없으면
            throw new AuthenticationServiceException("Username or Password not provided");
        }
        RestAuthenticationToken token = new RestAuthenticationToken(accountDTO.getUsername(),accountDTO.getPassword()); // RestAuthenticationToken 생성

        return this.getAuthenticationManager().authenticate(token); // RestAuthenticationToken을 인증 매니저에게 전달
    }
}
