package com.example.membercertification.domain.dto;

import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Data // Getter, Setter, toString, equals, hashCode 메소드를 자동 생성
public class AccountContext implements UserDetails { // AccountContext : 사용자 정보를 담는 클래스

    private AccountDTO accountDTO; // 사용자 정보
    private final List<GrantedAuthority> roles; // 사용자 권한 정보

    public AccountContext(AccountDTO accountDTO, List<GrantedAuthority> roles) {
        this.accountDTO = accountDTO;
        this.roles = roles;
    }
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles;
    }
    @Override
    public String getPassword() {
        return accountDTO.getPassword();
    }
    @Override
    public String getUsername() {
        return accountDTO.getUsername();
    }
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }
    @Override
    public boolean isEnabled() {
        return true;
    }
}
