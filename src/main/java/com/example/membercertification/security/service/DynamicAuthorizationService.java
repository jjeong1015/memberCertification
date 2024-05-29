package com.example.membercertification.security.service;

import com.example.membercertification.security.mapper.UrlRoleMapper;

import java.util.Map;

public class DynamicAuthorizationService { // // DynamicAuthorizationService : URL과 권한을 매핑하기 위해

    private final UrlRoleMapper delegate; // UrlRoleMapper 객체 생성
    public DynamicAuthorizationService(UrlRoleMapper delegate) {
        this.delegate = delegate; // UrlRoleMapper 객체를 매개변수로 받는 생성자
    }
    public Map<String, String> getUrlRoleMappings() {
            return delegate.getUrlRoleMappings(); // UrlRoleMapper 객체의 getUrlRoleMappings 메소드 호출
    }
}
