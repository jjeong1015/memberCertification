package com.example.membercertification.security.config;

import com.example.membercertification.admin.service.RoleHierarchyService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.hierarchicalroles.RoleHierarchyImpl;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class AuthConfig { // AuthConfig : 인증 관련 설정을 위한 클래스
    @Bean
    public PasswordEncoder passwordEncoder() { // PasswordEncoder 빈을 정의
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    @Bean
    public RoleHierarchyImpl roleHierarchy(RoleHierarchyService roleHierarchyService) { // RoleHierarchyImpl 빈을 정의
        String allHierarchy = roleHierarchyService.findAllHierarchy(); // 모든 계층을 가져옴
        RoleHierarchyImpl roleHierarchy = new RoleHierarchyImpl(); // RoleHierarchyImpl 객체 생성
        roleHierarchy.setHierarchy(allHierarchy); // 계층 설정

        return roleHierarchy; // RoleHierarchyImpl 객체 반환
    }
}
