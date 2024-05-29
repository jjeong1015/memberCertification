package com.example.membercertification.admin.repository;

import com.example.membercertification.domain.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserManagementRepository extends JpaRepository<Account, Long> { // UserManagementRepository : 사용자 정보를 관리하기 위한 인터페이스

}
