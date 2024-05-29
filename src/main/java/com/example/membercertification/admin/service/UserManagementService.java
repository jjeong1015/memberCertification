package com.example.membercertification.admin.service;

import com.example.membercertification.domain.dto.AccountDTO;
import com.example.membercertification.domain.entity.Account;

import java.util.List;

public interface UserManagementService { // UserManagementService : 사용자 정보를 관리하기 위한 인터페이스

    void modifyUser(AccountDTO accountDTO);

    List<Account> getUsers();
    AccountDTO getUser(Long id);

    void deleteUser(Long idx);

}
