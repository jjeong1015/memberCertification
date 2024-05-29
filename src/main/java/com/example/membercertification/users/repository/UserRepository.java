package com.example.membercertification.users.repository;

import com.example.membercertification.domain.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<Account, Long> { // UserRepository : 사용자 정보를 관리하기 위한 인터페이스
    Account findByUsername(String username); // 사용자 이름으로 사용자 정보 조회
}
