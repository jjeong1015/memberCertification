package com.example.membercertification.domain.entity;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;

@Entity
@Table(name = "ROLE")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Role implements Serializable { // Role : 사용자의 권한을 관리하기 위한 엔티티 클래스
    @Id
    @GeneratedValue
    @Column(name = "role_id")
    private Long id;

    @Column(name = "role_name")
    private String roleName;

    @Column(name = "role_desc")
    private String roleDesc;

    @Column(name = "is_expression")
    private String isExpression;

    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "roleSet", cascade = CascadeType.ALL)
    @OrderBy("orderNum desc")
    @Builder.Default
    private Set<Resources> resourcesSet = new LinkedHashSet<>();

    @Builder.Default
    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "userRoles", cascade = CascadeType.ALL)
    private Set<Account> accounts = new HashSet<>();
}