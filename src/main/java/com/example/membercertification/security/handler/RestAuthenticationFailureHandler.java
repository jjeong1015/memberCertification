package com.example.membercertification.security.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component("restFailureHandler")
public class RestAuthenticationFailureHandler implements AuthenticationFailureHandler { // RestAuthenticationFailureHandler : 로그인 실패 시, JSON으로 에러 메시지를 전달하기 위한 클래스

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException {

        ObjectMapper mapper = new ObjectMapper();

        response.setStatus(HttpStatus.UNAUTHORIZED.value()); // 401
        response.setContentType(MediaType.APPLICATION_JSON_VALUE); // application/json

        if (exception instanceof BadCredentialsException) { // BadCredentialsException이면
            mapper.writeValue(response.getWriter(), "Invalid username or password"); // "Invalid username or password"를 JSON으로 변환하여 응답
        }
        mapper.writeValue(response.getWriter(), "Authentication failed"); // "Authentication failed"를 JSON으로 변환하여 응답
    }
}