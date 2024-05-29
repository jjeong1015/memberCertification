package com.example.membercertification.security.detail;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.authentication.AuthenticationDetailsSource;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.stereotype.Component;

@Component // 빈으로 등록
public class FormWebAuthenticationDetailsSource implements AuthenticationDetailsSource<HttpServletRequest, WebAuthenticationDetails> { // FormWebAuthenticationDetailsSource : 사용자가 입력한 secretKey 값을 가져오기 위한 클래스

    @Override
    public WebAuthenticationDetails buildDetails(HttpServletRequest request) { // HttpServletRequest를 받아서 WebAuthenticationDetails 객체를 생성
        return new FormAuthenticationDetails(request); // FormAuthenticationDetails 객체 생성
    }
}
