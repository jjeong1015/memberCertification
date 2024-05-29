package com.example.membercertification.admin.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AdminController { // AdminController : 관리자 페이지를 관리하기 위한 클래스

    @GetMapping("/admin/home")
    public String home() {
        return "admin/home";
    }
}
