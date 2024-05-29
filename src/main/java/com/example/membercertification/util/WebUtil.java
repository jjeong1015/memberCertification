package com.example.membercertification.util;

import jakarta.servlet.http.HttpServletRequest;

public class WebUtil { // 웹 관련 유틸 클래스

    private static final String XML_HTTP_REQUEST = "XMLHttpRequest";
    private static final String X_REQUESTED_WITH = "X-Requested-With";
    private static final String CONTENT_TYPE = "Content-Type";
    private static final String CONTENT_TYPE_JSON = "application/json";

    public static boolean isAjax(HttpServletRequest request) { // 요청이 Ajax 요청인지 확인
        return XML_HTTP_REQUEST.equals(request.getHeader(X_REQUESTED_WITH));
    }

    public static boolean isContentTypeJson(HttpServletRequest request) { // 요청이 JSON 요청인지 확인
        return request.getHeader(CONTENT_TYPE).contains(CONTENT_TYPE_JSON);
    }
}
