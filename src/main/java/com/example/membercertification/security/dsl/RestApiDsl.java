package com.example.membercertification.security.dsl;

import com.example.membercertification.security.filter.RestAuthenticationFilter;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.HttpSecurityBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractAuthenticationFilterConfigurer;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.RememberMeServices;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.session.SessionAuthenticationStrategy;
import org.springframework.security.web.context.SecurityContextRepository;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;

public class RestApiDsl <H extends HttpSecurityBuilder<H>> extends
        AbstractAuthenticationFilterConfigurer<H, RestApiDsl<H>, RestAuthenticationFilter> { // RestApiDsl : Rest 방식으로 로그인하기 위한 Dsl 클래스

    private AuthenticationSuccessHandler successHandler; // 로그인 성공 핸들러
    private AuthenticationFailureHandler failureHandler; // 로그인 실패 핸들러

    public RestApiDsl(){ // 생성자
        super(new RestAuthenticationFilter(),null); // RestAuthenticationFilter 객체 생성
    }
    @Override
    public void init(H http) throws Exception { // 초기화 메소드
        super.init(http);
    }
    @Override
    public void configure(H http) { // 설정 메소드

        AuthenticationManager authenticationManager = http.getSharedObject(AuthenticationManager.class);
        getAuthenticationFilter().setAuthenticationManager(authenticationManager);
        getAuthenticationFilter().setAuthenticationSuccessHandler(successHandler);
        getAuthenticationFilter().setAuthenticationFailureHandler(failureHandler);
        getAuthenticationFilter().setSecurityContextRepository(getAuthenticationFilter().getSecurityContextRepository((HttpSecurity) http));

        SessionAuthenticationStrategy sessionAuthenticationStrategy = http.getSharedObject(SessionAuthenticationStrategy.class);
        if (sessionAuthenticationStrategy != null) {
            getAuthenticationFilter().setSessionAuthenticationStrategy(sessionAuthenticationStrategy);
        }
        RememberMeServices rememberMeServices = http.getSharedObject(RememberMeServices.class);
        if (rememberMeServices != null) {
            getAuthenticationFilter().setRememberMeServices(rememberMeServices);
        }
        http.setSharedObject(RestAuthenticationFilter.class,getAuthenticationFilter());
        http.addFilterBefore(getAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);
    }

    public RestApiDsl<H> restSuccessHandler(AuthenticationSuccessHandler successHandler) { // 로그인 성공 핸들러 설정
        this.successHandler = successHandler;
        return this;
    }

    public RestApiDsl<H> restFailureHandler(AuthenticationFailureHandler authenticationFailureHandler) { // 로그인 실패 핸들러 설정
        this.failureHandler = authenticationFailureHandler;
        return this;
    }

    public RestApiDsl<H> loginPage(String loginPage) { // 로그인 페이지 설정
        return super.loginPage(loginPage);
    }

    public RestApiDsl<H> setSecurityContextRepository(SecurityContextRepository securityContextRepository) { // SecurityContextRepository 설정
        return super.securityContextRepository(securityContextRepository);
    }

    @Override
    protected RequestMatcher createLoginProcessingUrlMatcher(String loginProcessingUrl) { // 로그인 처리 URL 설정
        return new AntPathRequestMatcher(loginProcessingUrl, "POST");
    }

}
