package com.daol.logbuffer.member.query;

import com.querydsl.core.annotations.QueryProjection;
import java.util.UUID;

public record CommentAuthorResponse(
    UUID authorId,
    String name,
    String authorType,
    String profileImageUrl
) {

    @QueryProjection
    public CommentAuthorResponse {
    }
}