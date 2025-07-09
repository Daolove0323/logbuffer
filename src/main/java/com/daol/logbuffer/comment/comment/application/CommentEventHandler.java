package com.daol.logbuffer.comment.comment.application;

import com.daol.logbuffer._common.event.CommentCreatedEvent;
import com.daol.logbuffer._common.event.CommentDeletedEvent;
import com.daol.logbuffer.postmeta.PostMetaService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public class CommentEventHandler {

    private final PostMetaService postMetaService;

    @EventListener(CommentCreatedEvent.class)
    public void handleCommentCreatedEvent(CommentCreatedEvent event) {
        postMetaService.incrementCommentCount(event.getPostId());
    }

    @EventListener(CommentDeletedEvent.class)
    public void handleCommentDeletedEvent(CommentDeletedEvent event) {
        postMetaService.decrementCommentCount(event.getPostId());
    }
}