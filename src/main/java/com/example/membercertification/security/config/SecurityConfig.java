package com.example.membercertification.security.config;

import com.example.membercertification.security.dsl.RestApiDsl;
import com.example.membercertification.security.entrypoint.RestAuthenticationEntryPoint;
import com.example.membercertification.security.filter.RestAuthenticationFilter;
import com.example.membercertification.security.handler.*;
import com.example.membercertification.security.manager.CustomDynamicAuthorizationManager;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationDetailsSource;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authorization.AuthorizationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.intercept.RequestAuthorizationContext;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.WebAuthenticationDetails;

@EnableWebSecurity // Spring Security를 사용하도록 설정
@Configuration // 설정 클래스를 나타냄
@RequiredArgsConstructor // final 필드를 가지는 생성자를 생성
public class SecurityConfig { // Spring Security 설정을 위한 클래스

    private final AuthenticationProvider authenticationProvider;
    private final AuthenticationProvider restAuthenticationProvider;
    private final AuthenticationDetailsSource<HttpServletRequest, WebAuthenticationDetails> authenticationDetailsSource;
    private final FormAuthenticationSuccessHandler successHandler;
    private final FormAuthenticationFailureHandler failureHandler;
    private final RestAuthenticationSuccessHandler restSuccessHandler;
    private final RestAuthenticationFailureHandler restFailureHandler;
    private final AuthorizationManager<RequestAuthorizationContext> authorizationManager;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(auth -> auth // HttpSecurity 객체를 사용하여 요청에 대한 보안을 구성
//                        .requestMatchers("/css/**", "/images/**", "/js/**", "favicon.*", "/*/icon-*").permitAll() // 정적 자원은 모든 사용자에게 허용
//                        .requestMatchers("/", "signup", "/login*").permitAll() // 루트 경로는 모든 사용자에게 허용
//                        .requestMatchers("/user").hasAuthority("ROLE_USER")
//                        .requestMatchers("/manager").hasAuthority("ROLE_MANAGER")
//                        .requestMatchers("/admin").hasAuthority("ROLE_ADMIN")
//                        .anyRequest().authenticated()) // 그 외의 모든 요청은 인증된 사용자에게만 허용
                        .anyRequest().access(authorizationManager))
                .formLogin(form -> form
                        .loginPage("/login")
                        .authenticationDetailsSource(authenticationDetailsSource) // 사용자 정의 인증 정보를 사용
                        .successHandler(successHandler) // 로그인 성공 시 핸들러를 사용
                        .failureHandler(failureHandler) // 로그인 실패 시 핸들러를 사용
                        .permitAll()) // 로그인 페이지를 /login으로 지정하고 모든 사용자에게 허용
//                        .authenticationDetailsSource(authenticationDetailsSource)) // 사용자 정의 인증 정보를 사용
//                .userDetailsService(userDetailsService);
//                    .csrf(AbstractHttpConfigurer::disable)
                .authenticationProvider(authenticationProvider) // 사용자 인증을 처리하는 Provider를 사용
                .exceptionHandling(exception -> exception // 예외 처리를 위한 핸들러를 사용
                        .accessDeniedHandler(new FormAccessDeniedHandler("/denied")) // 접근 거부 시 핸들러를 사용
        );
        return http.build();
    }

    @Bean
    @Order(1)
    public SecurityFilterChain restSecurityFilterChain(HttpSecurity http) throws Exception {

        AuthenticationManagerBuilder authenticationManagerBuilder = http.getSharedObject(AuthenticationManagerBuilder.class);
        authenticationManagerBuilder.authenticationProvider(restAuthenticationProvider);
        AuthenticationManager authenticationManager = authenticationManagerBuilder.build();


        http
                .securityMatcher("/api/**") // /api/** 경로에 대한 보안 설정 -> rest/login 불러오기
                .authorizeHttpRequests(auth -> auth // HttpSecurity 객체를 사용하여 요청에 대한 보안을 구성
                        .requestMatchers("/css/**", "/images/**", "/js/**", "/favicon.*", "/*/icon-*").permitAll()
                        .requestMatchers("/api", "/api/login").permitAll()
                        .requestMatchers("/api/user").hasAuthority("ROLE_USER")
                        .requestMatchers("/api/manager").hasAuthority("ROLE_MANAGER")
                        .requestMatchers("/api/admin").hasAuthority("ROLE_ADMIN")
                        .anyRequest().authenticated()) // 그 외의 모든 요청은 인증된 사용자에게만 허용
                //.csrf(AbstractHttpConfigurer::disable) // CSRF 보안 설정을 해제
                //.addFilterBefore(restAuthenticationFilter(http, authenticationManager), UsernamePasswordAuthenticationFilter.class) // RestAuthenticationFilter를 UsernamePasswordAuthenticationFilter 앞에 추가 (dsl에서 제거)
                .authenticationManager(authenticationManager) // 인증 매니저 설정
                .exceptionHandling(exception -> exception // 예외 처리를 위한 핸들러를 사용
                        .authenticationEntryPoint(new RestAuthenticationEntryPoint()) // 인증 실패 시 핸들러를 사용
                        .accessDeniedHandler(new RestAccessDeniedHandler())) // 인증 성공 & 접근 거부 시 핸들러를 사용
                .with(new RestApiDsl<>(), restDsl -> restDsl // RestApiDsl을 사용하여 설정
                        .restSuccessHandler(restSuccessHandler) // 로그인 성공 핸들러를 사용
                        .restFailureHandler(restFailureHandler) // 로그인 실패 핸들러를 사용
                        .loginPage("/api/login") // 로그인 페이지를 /api/login으로 지정
                        .loginProcessingUrl("/api/login")) // 로그인 처리 URL을 /api/login으로 지정
        ;
        return http.build();
    }

//    private RestAuthenticationFilter restAuthenticationFilter(HttpSecurity http, AuthenticationManager authenticationManager) { // RestAuthenticationFilter 빈을 정의 (dsl에서 제거)
//        RestAuthenticationFilter restAuthenticationFilter = new RestAuthenticationFilter(); // RestAuthenticationFilter 객체 생성
//        restAuthenticationFilter.setAuthenticationManager(authenticationManager); // 인증 매니저 설정
//        restAuthenticationFilter.setAuthenticationSuccessHandler(restSuccessHandler);
//        restAuthenticationFilter.setAuthenticationFailureHandler(restFailureHandler);
//
//        return restAuthenticationFilter; // RestAuthenticationFilter 객체 반환
//    }

//    @Bean
//    public UserDetailsService userDetailsService() { // 사용자 정보를 제공하는 UserDetailsService 빈을 정의
//        UserDetails user = User.withUsername("user").password("{noop}1111").roles("USER").build(); // 사용자 이름, 비밀번호, 역할을 가진 UserDetails 객체를 생성
//        return new InMemoryUserDetailsManager(user);
//    }
}
