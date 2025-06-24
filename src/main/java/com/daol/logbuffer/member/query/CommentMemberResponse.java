package com.daol.logbuffer.member.query;

import com.querydsl.core.annotations.QueryProjection;
import java.util.UUID;

public record CommentMemberResponse(
    UUID authorId,
    String name,
    String profileImageUrl
) {

    @QueryProjection
    public CommentMemberResponse {
    }
}