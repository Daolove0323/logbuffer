package com.daol.logbuffer.comment.application;

import com.daol.logbuffer.comment.command.Comment;
import java.time.LocalDateTime;
import java.util.UUID;

public record CommentUpdateResponse(
    UUID commentId,
    LocalDateTime modifiedDate
) {

    public static CommentUpdateResponse from(Comment comment) {
        return new CommentUpdateResponse(
            comment.getId().getValue(),
            comment.getModifiedDate()
        );
    }
}