package com.example.membercertification.security.handler;

import com.example.membercertification.security.exception.SecretException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.CredentialsExpiredException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component("failureHandler")
public class FormAuthenticationFailureHandler extends SimpleUrlAuthenticationFailureHandler { // FormAuthenticationFailureHandler : 로그인 실패 시 로그인 페이지로 리다이렉트하고 에러 메시지를 전달하기 위한 클래스

    @Override
    public void onAuthenticationFailure(final HttpServletRequest request, final HttpServletResponse response, final AuthenticationException exception) throws IOException, ServletException { // 로그인 실패 시 처리할 메서드

        String errorMessage = "Invalid Username or Password"; // 기본 에러 메시지

        if(exception instanceof BadCredentialsException) { // 예외가 BadCredentialsException이면
            errorMessage = "Invalid Username or Password";
        }
        else if(exception instanceof UsernameNotFoundException) { // 예외가 UsernameNotFoundException이면
            errorMessage = "User not exists";
        }
        else if(exception instanceof CredentialsExpiredException) { // 예외가 CredentialsExpiredException이면
            errorMessage = "Expired password";

        }else if(exception instanceof SecretException) { // 예외가 SecretException이면
            errorMessage = "Invalid Secret key";
        }

        setDefaultFailureUrl("/login?error=true&exception=" + errorMessage); // 로그인 페이지로 리다이렉트

        super.onAuthenticationFailure(request, response, exception); // 부모 클래스의 메서드 호출
    }
}