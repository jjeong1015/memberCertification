package com.example.membercertification.admin.repository;

import com.example.membercertification.domain.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserManagementRepository extends JpaRepository<Account, Long> { }
