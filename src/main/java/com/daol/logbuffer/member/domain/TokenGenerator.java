package com.daol.logbuffer.member.domain;

import com.daol.logbuffer.member.auth.Token;
import com.daol.logbuffer.member.common.Grade;

public interface TokenGenerator {

    Token generateToken(MemberId memberId, Grade grade);

}