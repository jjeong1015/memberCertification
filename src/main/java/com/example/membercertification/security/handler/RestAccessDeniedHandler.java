package com.example.membercertification.security.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;

import java.io.IOException;

public class RestAccessDeniedHandler implements AccessDeniedHandler { // RestAccessDeniedHandler : 접근 거부 시, JSON으로 에러 메시지를 전달하기 위한 클래스

    private final ObjectMapper mapper = new ObjectMapper(); // JSON 변환을 위한 ObjectMapper 객체 생성

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException {
        response.setContentType(MediaType.APPLICATION_JSON_VALUE); // 응답의 Content-Type을 JSON으로 설정
        response.setStatus(HttpStatus.FORBIDDEN.value()); // 응답의 상태 코드를 403으로 설정
        response.getWriter().write(this.mapper.writeValueAsString(HttpServletResponse.SC_FORBIDDEN)); // 응답의 내용을 JSON으로 변환하여 출력
    }
}
