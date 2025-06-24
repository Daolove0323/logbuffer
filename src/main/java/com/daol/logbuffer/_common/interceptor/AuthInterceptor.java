package com.daol.logbuffer._common.interceptor;

import com.daol.logbuffer._common.argresolver.Auth;
import com.daol.logbuffer._common.exception.UnauthenticatedException;
import com.daol.logbuffer.member.auth.AuthMember;
import com.daol.logbuffer.member.auth.AuthService;
import com.daol.logbuffer.member.auth.Token;
import com.daol.logbuffer.member.infra.JwtUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.lang.reflect.Parameter;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public class AuthInterceptor implements HandlerInterceptor {

    // Todo: SpringSecurity로 대체

    @Value("${app.attribute.member}")
    private String MEMBER_ATTRIBUTE;
    private static final String AUTHORIZATION_HEADER = "Authorization";
    private static final String BEARER_PREFIX = "Bearer ";
    private static final int BEARER_PREFIX_LENGTH = BEARER_PREFIX.length();
    private final JwtUtil jwtUtil;
    private final AuthService authService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (!(handler instanceof HandlerMethod handlerMethod)) {
            return true;
        }

        // @Auth 애너테이션이 붙은 파라미터가 포함된 메서드만 인증/인가
        Auth auth = null;
        boolean requiresAuth = false;
        for (Parameter parameter : handlerMethod.getMethod().getParameters()) {
            if (parameter.isAnnotationPresent(Auth.class)) {
                requiresAuth = true;
                auth = parameter.getAnnotation(Auth.class);
                break;
            }
        }
        if (!requiresAuth) {
            return true;
        }

        // 토큰을 통한 인증/인가
        Token token = extractToken(request);
        AuthMember member = jwtUtil.parseTokenToMember(token);
        authService.validateMemberExists(member);
        authService.validateAuthorization(member, auth.value());

        request.setAttribute(MEMBER_ATTRIBUTE, member);
        return true;
    }

    private Token extractToken(HttpServletRequest request) {
        String bearerToken = request.getHeader(AUTHORIZATION_HEADER);
        if (!StringUtils.hasText(bearerToken) || !bearerToken.startsWith(BEARER_PREFIX)) {
            throw new UnauthenticatedException("토큰이 유효하지 않습니다.");
        }
        return new Token(bearerToken.substring(BEARER_PREFIX_LENGTH));
    }
}