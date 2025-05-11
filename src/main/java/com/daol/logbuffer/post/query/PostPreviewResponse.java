package com.daol.logbuffer.post.query;

import com.daol.logbuffer.member.PostMemberResponse;
import com.daol.logbuffer.post.command.post.PostState;
import com.daol.logbuffer.postmeta.HashtagName;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public record PostPreviewResponse(
    UUID postId,
    String title,
    String contentPreview,
    PostMemberResponse author,
    PostState state,
    String thumbnailUrl,
    Integer likeCount,
    Integer commentCount,
    String category,
    List<HashtagName> hashtags,
    LocalDateTime createdDate,
    LocalDateTime modifiedDate) {
}