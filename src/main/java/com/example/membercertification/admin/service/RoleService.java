package com.example.membercertification.admin.service;

import com.example.membercertification.domain.entity.Role;

import java.util.List;

public interface RoleService {
    Role getRole(long id);
    List<Role> getRoles();
    List<Role> getRolesWithoutExpression();

    void createRole(Role role);

    void deleteRole(long id);
}
