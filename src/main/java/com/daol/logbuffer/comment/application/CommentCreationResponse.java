package com.daol.logbuffer.comment.application;

import com.daol.logbuffer.comment.command.Comment;
import java.time.LocalDateTime;
import java.util.UUID;

public record CommentCreationResponse(
    UUID commentId,
    LocalDateTime createdDate
) {

    public static CommentCreationResponse from(Comment comment) {
        return new CommentCreationResponse(
            comment.getId().getValue(),
            comment.getCreatedDate()
        );
    }
}