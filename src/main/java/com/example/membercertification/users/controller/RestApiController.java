package com.example.membercertification.users.controller;

import com.example.membercertification.domain.dto.AccountDTO;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class RestApiController { // RestApiController : REST API를 관리하기 위한 클래스

    @GetMapping("/user")
    public AccountDTO restUser(@AuthenticationPrincipal AccountDTO accountDTO) { // 현재 사용자의 인증 객체를 반환
        return accountDTO;
    }

    @GetMapping("/manager")
    public AccountDTO restManager(@AuthenticationPrincipal AccountDTO accountDTO) { // 현재 사용자의 정보를 반환
        return accountDTO;
    }

    @GetMapping("/admin")
    public AccountDTO restAdmin(@AuthenticationPrincipal AccountDTO accountDTO) { // 현재 사용자의 정보를 반환
        return accountDTO;
    }

    @GetMapping( "/logout")
    public String logout(HttpServletRequest request, HttpServletResponse response) {  // 로그아웃 처리를 위해 request, response 필요
        Authentication authentication = SecurityContextHolder.getContextHolderStrategy().getContext().getAuthentication(); // 현재 사용자의 인증 정보를 가져옴
        if (authentication != null) { // 인증 정보가 존재하면
            new SecurityContextLogoutHandler().logout(request, response, authentication); // 로그아웃 처리
        }

        return "logout"; // 로그아웃 후 /login으로 리다이렉트
    }
}
