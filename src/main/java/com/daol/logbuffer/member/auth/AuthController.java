package com.daol.logbuffer.member.auth;

import com.daol.logbuffer._common.api.ApiResponse;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public class AuthController {

    private final AuthService authService;

    @PostMapping("/signup")
    public ResponseEntity<Token> signUp(@RequestBody @Valid SignUpRequest signUpReq) {
        return ApiResponse.created(authService.signUp(signUpReq));
    }

    @PostMapping("/login")
    public ResponseEntity<Token> login(@RequestBody @Valid LoginRequest loginReq) {
        return ApiResponse.ok(authService.login(loginReq));
    }

    // Todo: 추후 redis를 통한 세션 사용 및 로그아웃 구현
}