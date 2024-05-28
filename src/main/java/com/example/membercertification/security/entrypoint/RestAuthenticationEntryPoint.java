package com.example.membercertification.security.entrypoint;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import java.io.IOException;

public class RestAuthenticationEntryPoint implements AuthenticationEntryPoint { // 인증 진입점

    private final ObjectMapper mapper = new ObjectMapper(); // JSON 변환을 위한 ObjectMapper 객체 생성

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {

        response.setContentType(MediaType.APPLICATION_JSON_VALUE); // 응답의 Content-Type을 JSON으로 설정
        response.setStatus(HttpStatus.UNAUTHORIZED.value()); // 응답의 상태 코드를 401로 설정
        response.getWriter().write(mapper.writeValueAsString(HttpServletResponse.SC_UNAUTHORIZED)); // 응답의 내용을 JSON으로 변환하여 출력
    }
}
