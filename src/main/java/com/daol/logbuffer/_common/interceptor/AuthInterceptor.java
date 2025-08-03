package com.daol.logbuffer._common.interceptor;

import com.daol.logbuffer._common.argresolver.Auth;
import com.daol.logbuffer._common.exception.UnauthenticatedException;
import com.daol.logbuffer.member.auth.AuthService;
import com.daol.logbuffer.member.auth.CurrentUser;
import com.daol.logbuffer.member.auth.TokenResponse;
import com.daol.logbuffer.member.common.Grade;
import com.daol.logbuffer.member.domain.GuestId;
import com.daol.logbuffer.member.infra.JwtUtil;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.lang.reflect.Parameter;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;


@Component
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public class AuthInterceptor implements HandlerInterceptor {

    // Todo: Spring Security 사용 고려

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

        // @Current 애너테이션이 붙은 파라미터가 포함된 메서드만 인증/인가
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
        if (request.getHeader(AUTHORIZATION_HEADER) != null) {
            TokenResponse tokenResponse = extractToken(request);
            CurrentUser member = jwtUtil.parseTokenToMember(tokenResponse);
            authService.validateMemberExists(member);
            authService.validateAuthorization(member, auth.value());
            request.setAttribute(MEMBER_ATTRIBUTE, member);
            return true;
        }

        if (!auth.value().equals(Grade.GUEST)) {
            return false;
        }

        // 토큰이 없으면 쿠키를 통해 인증/인가
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("guest_id")) {
                    GuestId guestId = new GuestId(cookie.getValue());
                    CurrentUser user = new CurrentUser(guestId, Grade.GUEST);
                    request.setAttribute(MEMBER_ATTRIBUTE, user);
                    return true;
                }
            }
        }

        // 토큰과 쿠키가 없으면, 쿠키에 게스트 ID를 설정
        GuestId guestId = GuestId.generate();
        Cookie cookie = new Cookie("guest_id", guestId.toString());
        cookie.setPath("/");
        cookie.setMaxAge(60 * 60 * 24 * 365);
        response.addCookie(cookie);
        CurrentUser user = new CurrentUser(guestId, Grade.GUEST);
        request.setAttribute(MEMBER_ATTRIBUTE, user);
        return true;
    }

    private TokenResponse extractToken(HttpServletRequest request) {
        String bearerToken = request.getHeader(AUTHORIZATION_HEADER);
        if (!bearerToken.startsWith(BEARER_PREFIX)) {
            throw new UnauthenticatedException("토큰이 유효하지 않습니다.");
        }
        return new TokenResponse(bearerToken.substring(BEARER_PREFIX_LENGTH));
    }
}