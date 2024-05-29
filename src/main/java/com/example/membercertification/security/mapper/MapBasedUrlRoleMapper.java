package com.example.membercertification.security.mapper;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public class MapBasedUrlRoleMapper implements UrlRoleMapper{ // MapBasedUrlRoleMapper : URL과 권한을 매핑하는 클래스

    private final LinkedHashMap<String, String> urlRoleMappings = new LinkedHashMap<>(); // LinkedHashMap 객체 생성
    @Override
    public Map<String, String> getUrlRoleMappings() { // UrlRoleMapper 인터페이스의 getUrlRoleMappings 메소드 재정의, urlRoleMappings : URL과 권한을 매핑하는 맵 객체 반환

        urlRoleMappings.put("/", "permitAll"); // URL과 권한 매핑
        urlRoleMappings.put("/css/**", "permitAll");
        urlRoleMappings.put("/js/**", "permitAll");
        urlRoleMappings.put("/images/**", "permitAll");
        urlRoleMappings.put("/favicon.*", "permitAll");
        urlRoleMappings.put("/*/icon-*", "permitAll");

        urlRoleMappings.put("/signup", "permitAll");
        urlRoleMappings.put("/login", "permitAll");
        urlRoleMappings.put("/logout", "permitAll");

        urlRoleMappings.put("/denied", "authenticated");

        urlRoleMappings.put("/user", "ROLE_USER");
        urlRoleMappings.put("/admin/**", "ROLE_ADMIN");
        urlRoleMappings.put("/manager", "ROLE_MANAGER");

        urlRoleMappings.put("/db", "hasRole('DBA')");

        return new HashMap<>(urlRoleMappings); // urlRoleMappings 맵 객체 반환
    }
}