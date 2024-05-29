package com.example.membercertification.security.mapper;

import com.example.membercertification.admin.repository.ResourcesRepository;
import com.example.membercertification.domain.entity.Resources;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class PersistentUrlRoleMapper implements UrlRoleMapper{

    private final LinkedHashMap<String, String> urlRoleMappings = new LinkedHashMap<>();
    private final ResourcesRepository resourcesRepository;

    public PersistentUrlRoleMapper(ResourcesRepository resourcesRepository) {
        this.resourcesRepository = resourcesRepository;
    }

    @Override
    public Map<String, String> getUrlRoleMappings() {
        urlRoleMappings.clear();
        List<Resources> resourcesList = resourcesRepository.findAllResources();
        resourcesList.forEach(re -> {
            re.getRoleSet().forEach(role -> {
                urlRoleMappings.put(re.getResourceName(), role.getRoleName());
            });
        });
        return urlRoleMappings;
    }
}
