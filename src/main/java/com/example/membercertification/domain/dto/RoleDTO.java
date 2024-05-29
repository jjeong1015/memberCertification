package com.example.membercertification.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RoleDTO{ // RoleDTO : 사용자의 권한 정보를 전달하기 위한 DTO 클래스
    private String id;
    private String roleName;
    private String roleDesc;
    private String isExpression;
}
