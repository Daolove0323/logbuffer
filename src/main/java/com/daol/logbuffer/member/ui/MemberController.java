package com.daol.logbuffer.member.ui;

import com.daol.logbuffer._common.argresolver.Auth;
import com.daol.logbuffer.member.application.MemberService;
import com.daol.logbuffer.member.auth.CurrentUser;
import com.daol.logbuffer.member.common.Grade;
import com.daol.logbuffer.member.query.LoginMemberResponse;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/members")
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public class MemberController {

    private final MemberService memberService;

    @GetMapping("/me")
    public ResponseEntity<LoginMemberResponse> getMemberDetail(@Auth(Grade.NORMAL) CurrentUser user) {
        return ResponseEntity.ok(memberService.getLoginMember(user));
    }

    // Todo: 쿠키 방식 비로그인 회원 구현
    // Todo: Member update, delete 구현
}