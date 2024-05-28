package com.example.membercertification.security.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.example.membercertification.domain.dto.AccountContext;
import com.example.membercertification.domain.dto.AccountDTO;
import com.example.membercertification.domain.entity.Account;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.WebAttributes;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import java.io.IOException;

@Component("restSuccessHandler") // Bean 이름 설정
public class RestAuthenticationSuccessHandler implements AuthenticationSuccessHandler {
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {

        ObjectMapper mapper = new ObjectMapper();

        AccountDTO accountDTO = (AccountDTO) authentication.getPrincipal(); // 인증 객체에서 Principal을 가져옴
        response.setStatus(HttpStatus.OK.value()); // 상태 코드 설정
        response.setContentType(MediaType.APPLICATION_JSON_VALUE); // 컨텐츠 타입 설정
        accountDTO.setPassword(null); // 패스워드를 null로 설정
        mapper.writeValue(response.getWriter(), accountDTO); // AccountDTO를 JSON으로 변환하여 응답

        clearAuthenticationAttributes(request); // 인증 속성을 제거
    }
    protected final void clearAuthenticationAttributes(HttpServletRequest request) { // 인증 속성을 제거하는 메서드
        HttpSession session = request.getSession(false); // 세션을 가져옴
        if (session == null) { // 세션이 없으면
            return; // 종료
        }
        session.removeAttribute(WebAttributes.AUTHENTICATION_EXCEPTION); // 인증 성공해서  인증 예외 제거
    }
}
