package com.example.membercertification.security.detail;

import jakarta.servlet.http.HttpServletRequest;
import lombok.Getter;
import org.springframework.security.web.authentication.WebAuthenticationDetails;

@Getter // secretKey 필드의 getter 메서드 생성
public class FormAuthenticationDetails extends WebAuthenticationDetails {

    private final String secretKey; // secretKey 필드 선언

    public FormAuthenticationDetails(HttpServletRequest request) { // HttpServletRequest를 받아서 부모 클래스의 생성자 호출
        super(request);
        secretKey = request.getParameter("secret_key"); // secret_key 파라미터 값을 가져와서 secretKey 필드에 저장
    }
}
