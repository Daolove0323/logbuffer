package com.daol.logbuffer.post.application;

import com.daol.logbuffer.category.CategoryId;
import com.daol.logbuffer.hashtag.HashtagId;
import com.daol.logbuffer.member.domain.MemberId;
import com.daol.logbuffer.post.command.PostAuthorId;
import com.daol.logbuffer.post.command.PostState;
import java.util.List;
import java.util.UUID;

public record PostCreationCommand(

    String title,
    String description,
    String content,
    CategoryId categoryId,
    List<HashtagId> hashtagIds,
    PostState state,
    String thumbnailImageUrl,
    PostAuthorId authorId) {

    private PostCreationCommand(
        String title,
        String description,
        String content,
        UUID categoryId,
        List<UUID> hashtagIds,
        PostState state,
        String thumbnailImageUrl,
        MemberId memberId) {
        this(title, description, content, new CategoryId(categoryId),
            hashtagIds.stream().map(HashtagId::new).toList(), state, thumbnailImageUrl, new PostAuthorId(memberId));
    }

    public static PostCreationCommand from(PostCreationRequest req, MemberId authorId) {
        return new PostCreationCommand(
            req.title(),
            req.description(),
            req.content(),
            req.categoryId(),
            req.hashtagIds(),
            req.state(),
            req.thumbnailImageUrl(),
            authorId
        );
    }
}