package com.daol.logbuffer.comment.comment.application;

import com.daol.logbuffer._common.event.CommentCreatedEvent;
import com.daol.logbuffer._common.event.CommentDeletedEvent;
import com.daol.logbuffer.postmeta.PostMetaService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Service
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public class CommentEventHandler {

    private final PostMetaService postMetaService;

    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void handleCommentCreatedEvent(CommentCreatedEvent event) {
        postMetaService.incrementCommentCount(event.getPostId());
    }

    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void handleCommentDeletedEvent(CommentDeletedEvent event) {
        postMetaService.decrementCommentCount(event.getPostId());
    }
}