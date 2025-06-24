package com.daol.logbuffer.post.query;

import com.daol.logbuffer.member.query.PostMemberResponse;
import com.daol.logbuffer.post.command.PostState;
import com.querydsl.core.annotations.QueryProjection;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public record PostDetailResponse(
    UUID postId,
    String title,
    String content,
    PostMemberResponse author,
    PostState state,
    String thumbnailImageUrl,
    Integer likeCount,
    Integer commentCount,
    String category,
    List<String> hashtags,
    LocalDateTime createdDate,
    LocalDateTime modifiedDate
) {

    @QueryProjection
    public PostDetailResponse {
    }
}