package com.daol.logbuffer.member.ui;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/members")
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public class MemberController {

    // Todo: 쿠키 방식 비로그인 회원 구현
    // Todo: Member read, update, delete 구현
}