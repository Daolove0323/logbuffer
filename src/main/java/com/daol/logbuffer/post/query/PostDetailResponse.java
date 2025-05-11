package com.daol.logbuffer.post.query;

import com.daol.logbuffer.member.PostMemberResponse;
import com.daol.logbuffer.post.command.post.PostState;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public record PostDetailResponse(
    UUID postId,
    String title,
    String content,
    PostMemberResponse author,
    PostState state,
    String postThumbnailImageUrl,
    Integer likeCount,
    Integer commentCount,
    String category,
    List<String> hashtags,
    LocalDateTime createdDate,
    LocalDateTime modifiedDate
) {

}