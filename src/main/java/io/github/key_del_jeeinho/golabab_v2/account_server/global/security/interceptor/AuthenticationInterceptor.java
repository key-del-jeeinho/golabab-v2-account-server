package io.github.key_del_jeeinho.golabab_v2.account_server.global.security.interceptor;

import org.springframework.http.HttpHeaders;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class AuthenticationInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String authorization = request.getHeader(HttpHeaders.AUTHORIZATION);
        if(authorization != null && authorization.startsWith("Bearer ")) {
            String token = authorization.substring("Bearer ".length());
            //TODO Authentication 서버 구축 후 구현
            //Authentication 서버에 요청을 보내서 토큰을 검증한다.
        }
        return HandlerInterceptor.super.preHandle(request, response, handler);
    }
}
