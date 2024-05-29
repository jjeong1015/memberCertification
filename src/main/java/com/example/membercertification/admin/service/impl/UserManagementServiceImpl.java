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
public class UserManagementServiceImpl implements UserManagementService { // UserManagementServiceImpl : 사용자 정보를 관리하기 위한 클래스

    private final UserManagementRepository userManagementRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    @Override
    public void modifyUser(AccountDTO accountDTO){ // 사용자 정보 수정
        ModelMapper modelMapper = new ModelMapper(); // ModelMapper : 객체 간의 매핑을 위한 라이브러리
        Account account = modelMapper.map(accountDTO, Account.class); // accountDTO를 Account로 매핑

        if(accountDTO.getRoles() != null){ // accountDTO의 roles가 null이 아닐 경우
            Set<Role> roles = new HashSet<>(); // roles : Role 객체를 담는 Set
            accountDTO.getRoles().forEach(role -> { // accountDTO의 roles를 반복
                Role r = roleRepository.findByRoleName(role); // roleRepository에서 roleName이 role인 Role 객체를 찾아 r에 저장
                roles.add(r); // roles에 r 추가
            });
            account.setUserRoles(roles); // account의 userRoles에 roles 추가
        }
        account.setPassword(passwordEncoder.encode(accountDTO.getPassword())); // account의 password에 accountDTO의 password를 암호화하여 저장
        userManagementRepository.save(account); // account 저장
    }

    @Transactional
    public AccountDTO getUser(Long id) { // 사용자 정보 조회
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
    public List<Account> getUsers() { // 사용자 정보 목록 조회
        return userManagementRepository.findAll();
    }

    @Override
    public void deleteUser(Long id) { // 사용자 정보 삭제
        userManagementRepository.deleteById(id);
    }

}