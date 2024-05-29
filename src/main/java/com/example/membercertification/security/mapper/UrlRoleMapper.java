package com.example.membercertification.security.mapper;

import java.util.Map;

public interface UrlRoleMapper { // UrlRoleMapper : URL과 권한을 매핑하는 인터페이스

    Map<String, String> getUrlRoleMappings();
}
