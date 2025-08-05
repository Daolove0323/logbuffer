package com.daol.logbuffer.member.query;

import com.daol.logbuffer.member.common.Grade;
import com.querydsl.core.annotations.QueryProjection;
import java.util.UUID;

public record LoginMemberResponse(
    UUID memberId,
    String email,
    String name,
    Grade grade,
    String profileImageUrl
) {

    @QueryProjection
    public LoginMemberResponse {
    }
}