package com.example.membercertification.security.manager;

import com.example.membercertification.admin.repository.ResourcesRepository;
import com.example.membercertification.security.mapper.MapBasedUrlRoleMapper;
import com.example.membercertification.security.mapper.PersistentUrlRoleMapper;
import com.example.membercertification.security.service.DynamicAuthorizationService;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.hierarchicalroles.RoleHierarchyImpl;
import org.springframework.security.authorization.AuthenticatedAuthorizationManager;
import org.springframework.security.authorization.AuthorityAuthorizationManager;
import org.springframework.security.authorization.AuthorizationDecision;
import org.springframework.security.authorization.AuthorizationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.access.expression.DefaultHttpSecurityExpressionHandler;
import org.springframework.security.web.access.expression.WebExpressionAuthorizationManager;
import org.springframework.security.web.access.intercept.RequestAuthorizationContext;
import org.springframework.security.web.servlet.util.matcher.MvcRequestMatcher;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcherEntry;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerMappingIntrospector;

import java.util.List;
import java.util.function.Supplier;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class CustomDynamicAuthorizationManager implements AuthorizationManager<RequestAuthorizationContext> { // CustomDynamicAuthorizationManager : 동적인 URL과 권한을 매핑하는 클래스

    private List<RequestMatcherEntry<AuthorizationManager<RequestAuthorizationContext>>> mappings; // RequestMatcherEntry 객체를 요소로 갖는 List 객체 mappings 선언
    // private static final AuthorizationDecision DENY = new AuthorizationDecision(false);
    private static final AuthorizationDecision ACCESS = new AuthorizationDecision(true); // AuthorizationDecision 객체 DENY 선언
    private static HandlerMappingIntrospector handlerMappingIntrospector; // HandlerMappingIntrospector 객체 handlerMappingIntrospector 선언
    private final ResourcesRepository resourcesRepository; // ResourcesRepository 객체 resourcesRepository 선언

    private DynamicAuthorizationService dynamicAuthorizationService; // DynamicAuthorizationService 객체 dynamicAuthorizationService 선언
    private final RoleHierarchyImpl roleHierarchy; // RoleHierarchyImpl 객체 roleHierarchy 선언

    @PostConstruct
    public void mapping() { // mappings 필드 초기화, 초기화 이유 : 스프링 빈이 생성된 후에 초기화되어야 하는 필드를 초기화하기 위해 사용

        dynamicAuthorizationService = new DynamicAuthorizationService(new PersistentUrlRoleMapper(resourcesRepository)); // 역할 : 동적인 URL 권한 매핑을 위한 DynamicAuthorizationService 객체 생성

        setMapping(); // setMapping 메소드 호출
    }

    public void setMapping() { // 역할 : URL과 권한을 매핑하는 메소드
        mappings = dynamicAuthorizationService.getUrlRoleMappings() // 동적인 URL 권한 매핑을 위한 DynamicAuthorizationService 객체의 getUrlRoleMappings 메소드 호출
                .entrySet().stream() // Set 객체를 스트림으로 변환
                .map(entry -> new RequestMatcherEntry<>( // RequestMatcherEntry 객체 생성, RequestMatcherEntry : 요청과 권한을 매핑하는 클래스
                        new MvcRequestMatcher(handlerMappingIntrospector, entry.getKey()), // MvcRequestMatcher : 요청을 매핑하는 클래스, getKey : 매핑된 URL을 반환하는 메소드
                        customAuthorizationManager(entry.getValue()))) // customAuthorizationManager : 권한을 매핑하는 메소드, getValue : 매핑된 권한을 반환하는 메소드
                .collect(Collectors.toList()); // 스트림을 List 객체로 변환, 변환 이유 : 스트림을 List 객체로 변환하여 mappings 필드에 저장하기 위해 사용
    }

    @Override
    public AuthorizationDecision check(Supplier<Authentication> authentication, RequestAuthorizationContext request) { // AuthorizationManager 인터페이스의 check 메소드 재정의

        for (RequestMatcherEntry<AuthorizationManager<RequestAuthorizationContext>> mapping : this.mappings) { // mappings 필드의 요소를 하나씩 가져와서 mapping에 저장

            RequestMatcher matcher = mapping.getRequestMatcher(); // mapping 객체의 RequestMatcher를 가져와서 matcher에 저장, matcher : 요청을 매핑하는 클래스
            RequestMatcher.MatchResult matchResult = matcher.matcher(request.getRequest()); // matcher 객체의 matcher 메소드 호출, request.getRequest : 요청을 반환하는 메소드, matchResult : 매핑 결과를 저장하는 객체

            if (matchResult.isMatch()) { // matchResult 객체의 isMatch 메소드 호출, 매핑 결과가 일치하는 경우
                AuthorizationManager<RequestAuthorizationContext> manager = mapping.getEntry(); // mapping 객체의 getEntry 메소드 호출, manager : 권한을 매핑하는 클래스
                return manager.check(authentication, // manager 객체의 check 메소드 호출
                        new RequestAuthorizationContext(request.getRequest(), matchResult.getVariables())); // RequestAuthorizationContext 객체 생성, RequestAuthorizationContext : 요청과 매핑 결과를 매핑하는 클래스
            }
        }
        // return DENY;
        return ACCESS; // ACCESS 객체 반환, ACCESS : 권한을 부여하는 객체, ACCESS 객체 반환 이유 : 매핑 결과가 일치하지 않는 경우에도 권한을 부여하기 위해 사용
    }

    @Override
    public void verify(Supplier<Authentication> authentication, RequestAuthorizationContext object) { // AuthorizationManager 인터페이스의 verify 메소드 재정의
        AuthorizationManager.super.verify(authentication, object); // 역할 : 부모 클래스의 verify 메소드 호출, 호출 이유 : 부모 클래스의 verify 메소드를 호출하여 권한을 확인하기 위해 사용
    }

    private AuthorizationManager<RequestAuthorizationContext> customAuthorizationManager(String role) { // 역할 : 권한을 매핑하는 메소드

        if (role != null) { // role이 null이 아닌 경우
            if (role.startsWith("ROLE")) { // role이 ROLE로 시작하는 경우
                AuthorityAuthorizationManager<RequestAuthorizationContext> authorizationManager = AuthorityAuthorizationManager.hasAuthority(role); // AuthorityAuthorizationManager 객체 생성, hasAuthority : 권한을 부여하는 메소드
                authorizationManager.setRoleHierarchy(roleHierarchy); // authorizationManager 객체의 setRoleHierarchy 메소드 호출, roleHierarchy : 역할 계층을 설정하는 객체
                return authorizationManager; // authorizationManager 객체 반환

            } else{ // role이 ROLE로 시작하지 않는 경우
                DefaultHttpSecurityExpressionHandler handler = new DefaultHttpSecurityExpressionHandler(); // DefaultHttpSecurityExpressionHandler 객체 생성
                handler.setRoleHierarchy(roleHierarchy); // handler 객체의 setRoleHierarchy 메소드 호출, roleHierarchy : 역할 계층을 설정하는 객체
                WebExpressionAuthorizationManager authorizationManager = new WebExpressionAuthorizationManager(role); // WebExpressionAuthorizationManager 객체 생성, WebExpressionAuthorizationManager : 웹 표현식을 사용하여 권한을 부여하는 클래스
                authorizationManager.setExpressionHandler(handler); // authorizationManager 객체의 setExpressionHandler 메소드 호출, handler : 웹 표현식을 설정하는 객체
                return authorizationManager; // authorizationManager 객체 반환
            }
        }
        return null; // role이 null인 경우 null 반환
    }

    public synchronized void reload() { // 역할 : 동적인 URL 권한 매핑을 다시 로드하는 메소드
        mappings.clear(); // mappings 필드 초기화
        setMapping(); // setMapping 메소드 호출
    }
}
