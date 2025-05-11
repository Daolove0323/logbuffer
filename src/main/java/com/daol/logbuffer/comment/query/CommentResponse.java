package com.daol.logbuffer.comment.query;

import com.daol.logbuffer.comment.command.CommentState;
import com.daol.logbuffer.member.CommentMemberResponse;
import java.time.LocalDateTime;
import java.util.UUID;

public record CommentResponse(
    UUID commentId,
    String content,
    CommentState state,
    CommentMemberResponse author,
    LocalDateTime createdDate,
    LocalDateTime modifiedDate
) {

}