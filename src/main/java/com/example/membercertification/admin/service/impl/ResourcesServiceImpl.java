package com.example.membercertification.admin.service.impl;

import com.example.membercertification.admin.repository.ResourcesRepository;
import com.example.membercertification.admin.service.ResourcesService;
import com.example.membercertification.domain.entity.Resources;
import com.example.membercertification.security.manager.CustomDynamicAuthorizationManager;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ResourcesServiceImpl implements ResourcesService { // ResourcesServiceImpl : 사용자의 권한 정보를 관리하기 위한 클래스

    private final ResourcesRepository resourcesRepository;
    private final CustomDynamicAuthorizationManager authorizationManager;

    @Transactional
    public Resources getResources(long id) {
        return resourcesRepository.findById(id).orElse(new Resources());
    }

    @Transactional
    public List<Resources> getResources() {
        return resourcesRepository.findAll(Sort.by(Sort.Order.asc("orderNum")));
    }

    @Transactional
    public void createResources(Resources resources){
        resourcesRepository.save(resources);
        authorizationManager.reload();
    }

    @Transactional
    public void deleteResources(long id) {
        resourcesRepository.deleteById(id);
        authorizationManager.reload();
    }
}