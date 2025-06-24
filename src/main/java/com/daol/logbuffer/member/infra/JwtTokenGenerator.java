package com.daol.logbuffer.member.infra;

import com.daol.logbuffer.member.auth.Token;
import com.daol.logbuffer.member.common.Grade;
import com.daol.logbuffer.member.domain.MemberId;
import com.daol.logbuffer.member.domain.TokenGenerator;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public class JwtTokenGenerator implements TokenGenerator {

    private final JwtUtil jwtUtil;

    @Override
    public Token generateToken(MemberId memberId, Grade grade) {
        return jwtUtil.generateToken(memberId, grade);
    }
}