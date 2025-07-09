package com.daol.logbuffer.member.domain;

import com.daol.logbuffer.member.auth.TokenResponse;
import com.daol.logbuffer.member.common.Grade;

public interface TokenGenerator {

    TokenResponse generateToken(MemberId memberId, Grade grade);

}