package com.example.membercertification.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data // Getter, Setter, toString, equals, hashCode 메소드를 자동 생성
@Builder // 빌더 패턴을 사용할 수 있게 해줌
@NoArgsConstructor // 디폴트 생성자를 생성
@AllArgsConstructor // 모든 필드 값을 파라미터로 받는 생성자를 생성
public class AccountDTO { // 사용자 정보 DTO
    public String id;
    public String username;
    public String password;
    private int age;
    private String roles;
}
