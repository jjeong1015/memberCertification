package com.example.membercertification.admin.service;

import com.example.membercertification.domain.entity.Role;

import java.util.List;

public interface RoleService { // RoleService : 사용자의 권한 정보를 관리하기 위한 인터페이스
    Role getRole(long id);
    List<Role> getRoles();
    List<Role> getRolesWithoutExpression();

    void createRole(Role role);

    void deleteRole(long id);
}
