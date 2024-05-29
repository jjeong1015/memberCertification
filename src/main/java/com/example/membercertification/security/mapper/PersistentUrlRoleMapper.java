package com.example.membercertification.security.mapper;

import com.example.membercertification.admin.repository.ResourcesRepository;
import com.example.membercertification.domain.entity.Resources;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class PersistentUrlRoleMapper implements UrlRoleMapper{ // PersistentUrlRoleMapper : URL과 권한을 매핑하는 클래스

    private final LinkedHashMap<String, String> urlRoleMappings = new LinkedHashMap<>(); // LinkedHashMap 객체 생성
    private final ResourcesRepository resourcesRepository; // ResourcesRepository 객체 생성

    public PersistentUrlRoleMapper(ResourcesRepository resourcesRepository) { // ResourcesRepository 객체를 매개변수로 받는 생성자
        this.resourcesRepository = resourcesRepository;
    }

    @Override
    public Map<String, String> getUrlRoleMappings() { // UrlRoleMapper 인터페이스의 getUrlRoleMappings 메소드 재정의, urlRoleMappings : URL과 권한을 매핑하는 맵 객체 반환
        urlRoleMappings.clear(); // urlRoleMappings 맵 객체 초기화
        List<Resources> resourcesList = resourcesRepository.findAllResources(); // 모든 리소스를 찾아 List 객체에 저장
        resourcesList.forEach(re -> { // resourcesList 객체를 반복
            re.getRoleSet().forEach(role -> { // 리소스의 역할을 반복
                urlRoleMappings.put(re.getResourceName(), role.getRoleName()); // URL과 권한 매핑
            });
        });
        return urlRoleMappings; // urlRoleMappings 맵 객체 반환
    }
}

