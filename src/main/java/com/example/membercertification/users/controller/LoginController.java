package com.example.membercertification.users.controller;

import com.example.membercertification.domain.dto.AccountDTO;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.ui.Model;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class LoginController {

    @GetMapping("/login")
    public String login(@RequestParam(value = "error", required = false) String error,
                        @RequestParam(value = "exception", required = false) String exception, Model model){
        model.addAttribute("error", error);
        model.addAttribute("exception", exception);
        return "login/login";
    }

    @GetMapping("/api/login")
    public String restLogin() {
        return "rest/login";
    }

    @GetMapping("/signup")
    public String signup() {
        return "login/signup"; // login/signup.html로 이동
    }

    @GetMapping( "/logout")
    public String logout(HttpServletRequest request, HttpServletResponse response) { // 로그아웃 처리를 위해 request, response 필요
        Authentication authentication = SecurityContextHolder.getContextHolderStrategy().getContext().getAuthentication(); // 현재 사용자의 인증 정보를 가져옴
        if (authentication != null) { // 인증 정보가 존재하면
            new SecurityContextLogoutHandler().logout(request, response, authentication); // 로그아웃 처리
        }

        return "redirect:/login"; // 로그아웃 후 /login으로 리다이렉트
    }

    @GetMapping("/denied")
    public String accessDenied(@RequestParam(value = "exception", required = false) String exception, @AuthenticationPrincipal AccountDTO accountDTO, Model model) { // 접근 거부 페이지
        model.addAttribute("username", accountDTO.getUsername());
        model.addAttribute("exception", exception);

        return "login/denied"; // login/denied.html로 이동
    }
}
