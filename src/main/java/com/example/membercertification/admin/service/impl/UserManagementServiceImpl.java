package com.example.membercertification.admin.service.impl;

import com.example.membercertification.admin.repository.RoleRepository;
import com.example.membercertification.admin.repository.UserManagementRepository;
import com.example.membercertification.admin.service.UserManagementService;
import com.example.membercertification.domain.dto.AccountDTO;
import com.example.membercertification.domain.entity.Account;
import com.example.membercertification.domain.entity.Role;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@Service("userManagementService")
@RequiredArgsConstructor
public class UserManagementServiceImpl implements UserManagementService {

    private final UserManagementRepository userManagementRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    @Override
    public void modifyUser(AccountDTO accountDTO){
        ModelMapper modelMapper = new ModelMapper();
        Account account = modelMapper.map(accountDTO, Account.class);

        if(accountDTO.getRoles() != null){
            Set<Role> roles = new HashSet<>();
            accountDTO.getRoles().forEach(role -> {
                Role r = roleRepository.findByRoleName(role);
                roles.add(r);
            });
            account.setUserRoles(roles);
        }
        account.setPassword(passwordEncoder.encode(accountDTO.getPassword()));
        userManagementRepository.save(account);
    }

    @Transactional
    public AccountDTO getUser(Long id) {
        Account account = userManagementRepository.findById(id).orElse(new Account());
        ModelMapper modelMapper = new ModelMapper();
        AccountDTO accountDTO = modelMapper.map(account, AccountDTO.class);

        List<String> roles = account.getUserRoles()
                .stream()
                .map(Role::getRoleName)
                .collect(Collectors.toList());

        accountDTO.setRoles(roles);
        return accountDTO;
    }

    @Transactional
    public List<Account> getUsers() {
        return userManagementRepository.findAll();
    }

    @Override
    public void deleteUser(Long id) {
        userManagementRepository.deleteById(id);
    }

}