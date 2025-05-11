package com.daol.logbuffer.post.application;

import com.daol.logbuffer.post.command.PostState;
import java.util.List;
import java.util.UUID;

public record PostUpdateRequest(
    String title,
    String content,
    UUID categoryId,
    List<UUID> hashtagIds,
    PostState state,
    String thumbnailUrl) {

}