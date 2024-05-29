package com.example.membercertification.admin.repository;

import com.example.membercertification.domain.entity.RoleHierarchy;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleHierarchyRepository extends JpaRepository<RoleHierarchy, Long> { // RoleHierarchyRepository : 사용자의 권한 계층 정보를 관리하기 위한 인터페이스

}
