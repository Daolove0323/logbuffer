package com.daol.logbuffer.post.application;

import com.daol.logbuffer.member.domain.MemberId;
import com.daol.logbuffer.post.command.PostAuthorId;
import com.daol.logbuffer.post.command.PostId;
import java.util.UUID;

public record PostDeletionCommand(
    PostId postId,
    PostAuthorId authorId
) {

    private PostDeletionCommand(
        UUID postId,
        MemberId memberId
    ) {
        this(new PostId(postId), new PostAuthorId(memberId));
    }

    public static PostDeletionCommand from(UUID postId, MemberId authorId) {
        return new PostDeletionCommand(postId, authorId);
    }
}