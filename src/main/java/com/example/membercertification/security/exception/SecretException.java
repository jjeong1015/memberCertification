package com.example.membercertification.security.exception;

import org.springframework.security.core.AuthenticationException;

public class SecretException extends AuthenticationException { // 사용자가 입력한 secretKey 값이 "secret"이 아닌 경우 발생하는 예외 클래스
    public SecretException(String message) { // 예외 메시지를 받아서 부모 클래스의 생성자 호출
        super(message); // 부모 클래스의 생성자 호출
    }
}
