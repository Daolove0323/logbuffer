package com.daol.logbuffer.member.query;

import com.querydsl.core.annotations.QueryProjection;
import java.util.UUID;

public record PostMemberResponse(
    UUID id,
    String name,
    String profileImageUrl
) {

    @QueryProjection
    public PostMemberResponse {
    }
}