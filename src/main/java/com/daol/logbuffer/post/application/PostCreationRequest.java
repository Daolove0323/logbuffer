package com.daol.logbuffer.post.application;

import com.daol.logbuffer.post.command.post.PostState;
import java.util.List;
import java.util.UUID;

public record PostCreationRequest(
    String title,
    String content,
    UUID categoryId,
    List<UUID> hashtagIds,
    PostState state,
    String thumbnailUrl) {

}