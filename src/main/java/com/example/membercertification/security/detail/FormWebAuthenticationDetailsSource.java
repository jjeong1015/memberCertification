package com.example.membercertification.security.detail;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.authentication.AuthenticationDetailsSource;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.stereotype.Component;

@Component // 빈으로 등록
public class FormWebAuthenticationDetailsSource implements AuthenticationDetailsSource<HttpServletRequest, WebAuthenticationDetails> {
    @Override
    public WebAuthenticationDetails buildDetails(HttpServletRequest request) { // HttpServletRequest를 받아서 WebAuthenticationDetails 객체를 생성
        return new FormAuthenticationDetails(request); // FormAuthenticationDetails 객체 생성
    }
}
