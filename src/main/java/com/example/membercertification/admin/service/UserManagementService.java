package com.example.membercertification.admin.service;

import com.example.membercertification.domain.dto.AccountDTO;
import com.example.membercertification.domain.entity.Account;

import java.util.List;

public interface UserManagementService {

    void modifyUser(AccountDTO accountDTO);

    List<Account> getUsers();
    AccountDTO getUser(Long id);

    void deleteUser(Long idx);

}
