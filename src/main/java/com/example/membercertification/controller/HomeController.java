package com.example.membercertification.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController { // HomeController : 홈 화면을 보여주기 위한 컨트롤러 클래스
    @GetMapping("/")
    public String dashboard() {
        return "/dashboard";
    }

    @GetMapping("/user")
    public String user() {
        return "/user";
    }

    @GetMapping("/manager")
    public String manager() {
        return "/manager";
    }

    @GetMapping("/admin")
    public String admin() {
        return "/admin";
    }

    @GetMapping("/api")
    public String restDashboard() {
        return "rest/dashboard";
    }

    @GetMapping("/db")
    public String db() {
        return "/db";
    }
}