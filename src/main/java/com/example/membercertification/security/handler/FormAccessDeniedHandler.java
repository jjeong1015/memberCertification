package com.example.membercertification.security.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;


public class FormAccessDeniedHandler implements AccessDeniedHandler { // FormAccessDeniedHandler : 접근 거부 시, 에러 페이지로 리다이렉트하기 위한 클래스
    
    private final RedirectStrategy redirectStrategy = new DefaultRedirectStrategy(); // 리다이렉트 전략을 사용하여 리다이렉트
    private final String errorPage; // 에러 페이지 URL

    public FormAccessDeniedHandler(String errorPage) { // 에러 페이지 URL을 받아서 저장
        this.errorPage = errorPage; // 에러 페이지 URL 저장
    }

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException { // 접근 거부 시 처리할 메서드

        String deniedUrl = errorPage + "?exception=" + accessDeniedException.getMessage(); // 에러 페이지 URL에 예외 메시지를 붙여서 URL 생성
        redirectStrategy.sendRedirect(request, response, deniedUrl); // 해당 URL로 리다이렉트

    }
}
