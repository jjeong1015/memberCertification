package com.example.membercertification.users.service;

import com.example.membercertification.domain.entity.Account;
import com.example.membercertification.users.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    @Transactional
    public void createUser(Account account) {
        // 사용자 생성 로직
        userRepository.save(account);

    }
}
