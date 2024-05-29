package com.example.membercertification.users.controller;

import com.example.membercertification.domain.dto.AccountDTO;
import com.example.membercertification.domain.entity.Account;
import com.example.membercertification.users.service.UserService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
public class UserController { // UserController : 사용자 정보를 관리하기 위한 클래스

    private final PasswordEncoder passwordEncoder;
    private final UserService userService;

    @PostMapping("/signup")
    public String signup(AccountDTO accountDTO) {
        ModelMapper mapper = new ModelMapper(); // ModelMapper 객체 생성
        Account account = mapper.map(accountDTO, Account.class); // AccountDTO를 Account로 변환
        account.setPassword(passwordEncoder.encode(account.getPassword())); // 비밀번호 암호화
        userService.createUser(account); // 사용자 생성
        return "redirect:/"; // 메인 페이지로 이동
    }
}
