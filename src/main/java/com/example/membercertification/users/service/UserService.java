package com.example.membercertification.users.service;

import com.example.membercertification.admin.repository.RoleRepository;
import com.example.membercertification.domain.entity.Account;
import com.example.membercertification.domain.entity.Role;
import com.example.membercertification.users.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Set;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService { // UserService : 사용자 정보를 관리하기 위한 클래스

    private final UserRepository userRepository; // 사용자 정보 저장소
    private final RoleRepository roleRepository; // 역할 정보 저장소

    @Transactional
    public void createUser(Account account){ // 사용자 생성 메소드
        Role role = roleRepository.findByRoleName("ROLE_USER"); // 사용자 역할 조회
        Set<Role> roles = new HashSet<>(); // 역할 정보 저장
        roles.add(role); // 사용자 역할 추가
        account.setUserRoles(roles); // 사용자 역할 설정
        userRepository.save(account); // 사용자 정보 저장
    }
}
