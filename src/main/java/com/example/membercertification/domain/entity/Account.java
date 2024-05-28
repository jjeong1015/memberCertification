package com.example.membercertification.domain.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Data;

@Data
@Entity
public class Account {
    @Id @GeneratedValue
    public Long id;
    public String username;
    public String password;
    private int age;
    private String roles;
}
