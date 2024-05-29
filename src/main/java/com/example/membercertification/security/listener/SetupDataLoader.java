package com.example.membercertification.security.listener;

import com.example.membercertification.admin.repository.RoleRepository;
import com.example.membercertification.domain.entity.Account;
import com.example.membercertification.domain.entity.Role;
import com.example.membercertification.users.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Set;

@Component
@RequiredArgsConstructor
public class SetupDataLoader implements ApplicationListener<ContextRefreshedEvent> { // SetupDataLoader : 데이터베이스에 초기 데이터를 설정하는 클래스

    private boolean alreadySetup = false; // 데이터 설정 여부
    private final UserRepository userRepository; // 사용자 정보 저장소
    private final RoleRepository roleRepository; // 역할 정보 저장소
    private final PasswordEncoder passwordEncoder; // 비밀번호 암호화

    @Override
    @Transactional
    public void onApplicationEvent(final ContextRefreshedEvent event) { // 스프링 컨텍스트 리프레시 이벤트 발생 시 호출
        if (alreadySetup) { // 데이터 설정이 이미 되어있으면 리턴
            return;
        }
        setupData(); // 데이터 설정 메서드 호출
        alreadySetup = true; // 데이터 설정 완료
    }

    private void setupData() { // 데이터 설정 메서드
        HashSet<Role> roles = new HashSet<>(); // 역할 정보 저장
        Role adminRole = createRoleIfNotFound("ROLE_ADMIN", "관리자"); // 관리자 역할 생성
        roles.add(adminRole); // 관리자 역할 추가
        createUserIfNotFound("admin", "admin@admin.com", "pass", roles); // 관리자 사용자 생성
    }

    public Role createRoleIfNotFound(String roleName, String roleDesc) { // 역할 정보가 없으면 역할 생성
        Role role = roleRepository.findByRoleName(roleName); // 역할 이름으로 역할 정보 조회

        if (role == null) { // 역할 정보가 없으면 역할 생성
            role = Role.builder() // 역할 정보 생성
                    .roleName(roleName) // 역할 이름 설정
                    .roleDesc(roleDesc) // 역할 설명 설정
                    .isExpression("N") // 표현식 여부 설정
                    .build(); // 역할 정보 생성
        }
        return roleRepository.save(role); // 역할 정보 저장
    }

    public void createUserIfNotFound(final String userName, final String email, final String password, Set<Role> roleSet) { // 사용자 정보가 없으면 사용자 생성
        Account account = userRepository.findByUsername(userName); // 사용자 이름으로 사용자 정보 조회

        if (account == null) { // 사용자 정보가 없으면 사용자 생성
            account = Account.builder() // 사용자 정보 생성
                    .username(userName) // 사용자 이름 설정
                    .password(passwordEncoder.encode(password)) // 비밀번호 암호화
                    .userRoles(roleSet) // 사용자 역할 설정
                    .build(); // 사용자 정보 생성
        }
        userRepository.save(account); // 사용자 정보 저장
    }
}
