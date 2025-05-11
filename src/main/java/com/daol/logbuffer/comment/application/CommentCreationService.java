package com.daol.logbuffer.comment.application;

import com.daol.logbuffer._common.event.Events;
import com.daol.logbuffer.comment.command.Comment;
import com.daol.logbuffer.comment.command.CommentAuthorId;
import com.daol.logbuffer.comment.command.CommentCreatedEvent;
import com.daol.logbuffer.comment.command.CommentRepository;
import com.daol.logbuffer.post.command.post.PostId;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public class CommentCreationService {

    private final CommentRepository commentRepository;

    @Transactional
    public CommentCreationResponse createComment(CommentAuthorId authorId, PostId postId, CommentCreationRequest commentReq) {
        Comment comment = Comment.create(commentReq.content(), postId, authorId, commentReq.isHidden());
        commentRepository.save(comment);
        Events.raise(new CommentCreatedEvent());
        return CommentCreationResponse.from(comment);
    }
}