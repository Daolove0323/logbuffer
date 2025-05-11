package com.daol.logbuffer.member;

import java.util.UUID;

public record CommentMemberResponse(
    UUID authorId,
    String name,
    String profileImageUrl
) {

}