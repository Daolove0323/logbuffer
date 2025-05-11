package com.daol.logbuffer.post.application;


import com.daol.logbuffer.post.command.Post;
import java.time.LocalDateTime;
import java.util.UUID;

public record PostUpdateResponse(
    UUID postId,
    LocalDateTime modifiedDate
) {

    public static PostUpdateResponse from(Post post) {
        return new PostUpdateResponse(post.getId().getValue(), post.getModifiedDate());
    }
}