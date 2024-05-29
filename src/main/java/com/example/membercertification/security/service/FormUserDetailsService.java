package com.example.membercertification.security.service;

import com.example.membercertification.domain.dto.AccountContext;
import com.example.membercertification.domain.dto.AccountDTO;
import com.example.membercertification.domain.entity.Account;
import com.example.membercertification.domain.entity.Role;
import com.example.membercertification.users.repository.UserRepository;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service("userDetailsService") // 스프링 시큐리티에서 사용자 정보를 가져오는 인터페이스를 구현한 클래스를 빈으로 등록
@RequiredArgsConstructor // final 필드를 가지는 생성자를 생성
public class FormUserDetailsService implements UserDetailsService { // FormUserDetailsService : 사용자 정보를 가져오는 클래스

    private final UserRepository userRepository;

    @Transactional
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException { // 사용자 이름을 받아서 사용자 정보를 조회하는 메서드

        Account account = userRepository.findByUsername(username); // 사용자 이름으로 사용자 정보 조회
        if (account == null) { // 사용자 정보가 없으면 예외 발생
            throw new UsernameNotFoundException("No user found with username: " + username);
        }

        List<GrantedAuthority> authorities = account.getUserRoles() // JPA에 맞게 변경
                .stream()
                .map(Role::getRoleName)
                .collect(Collectors.toSet())
                .stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList());
        ModelMapper mapper = new ModelMapper(); // ModelMapper 객체 생성
        AccountDTO accountDTO = mapper.map(account, AccountDTO.class); // Account를 AccountDTO로 변환

        return new AccountContext(accountDTO, authorities); // AccountContext 객체 생성
    }
}
