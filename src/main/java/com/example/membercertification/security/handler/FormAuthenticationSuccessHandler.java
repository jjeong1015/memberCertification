package com.example.membercertification.security.handler;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.RequestCache;
import org.springframework.security.web.savedrequest.SavedRequest;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component("successHandler")
public class FormAuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler { // FormAuthenticationSuccessHandler : 로그인 성공 시 이전 페이지로 리다이렉트하기 위해 사용하는 클래스

    private final RequestCache requestCache = new HttpSessionRequestCache(); // 요청 캐시를 사용하여 요청을 저장
    private final RedirectStrategy redirectStrategy = new DefaultRedirectStrategy(); // 리다이렉트 전략을 사용하여 리다이렉트

    @Override
    public void onAuthenticationSuccess(final HttpServletRequest request, final HttpServletResponse response, final Authentication authentication) throws IOException { // 로그인 성공 시 처리할 메서드

        setDefaultTargetUrl("/"); // 기본 리다이렉트 URL 설정

        SavedRequest savedRequest = requestCache.getRequest(request, response); // 요청 캐시에서 요청을 가져옴

        if(savedRequest!=null) { // 요청이 존재하면
            String targetUrl = savedRequest.getRedirectUrl(); // 요청 URL을 가져와서
            redirectStrategy.sendRedirect(request, response, targetUrl); // 해당 URL로 리다이렉트
        }
        else { // 요청이 존재하지 않으면
            redirectStrategy.sendRedirect(request, response, getDefaultTargetUrl()); // 기본 리다이렉트 URL로 리다이렉트
        }
    }
}
