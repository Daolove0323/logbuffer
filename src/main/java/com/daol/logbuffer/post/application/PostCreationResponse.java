package com.daol.logbuffer.post.application;


import com.daol.logbuffer.post.command.Post;
import java.time.LocalDateTime;
import java.util.UUID;

public record PostCreationResponse(
    UUID postId,
    LocalDateTime createdDate
) {

    public static PostCreationResponse from(Post post) {
        return new PostCreationResponse(post.getId().getValue(), post.getCreatedDate());
    }
}