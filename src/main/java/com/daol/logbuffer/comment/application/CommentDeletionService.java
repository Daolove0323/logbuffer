package com.daol.logbuffer.comment.application;

import com.daol.logbuffer._common.event.CommentDeletedEvent;
import com.daol.logbuffer._common.event.Events;
import com.daol.logbuffer._common.exception.EntityNotFoundException;
import com.daol.logbuffer.comment.command.Comment;
import com.daol.logbuffer.comment.command.CommentAuthorId;
import com.daol.logbuffer.comment.command.CommentId;
import com.daol.logbuffer.comment.command.CommentRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public class CommentDeletionService {

    private final CommentRepository commentRepository;

    @Transactional
    public void deleteComment(CommentAuthorId authorId, CommentId commentId) {
        Comment comment = commentRepository.findById(commentId)
            .orElseThrow(() -> new EntityNotFoundException("ID에 해당하는 댓글을 찾을 수 없습니다."));
        comment.verifyAuthor(authorId);
        commentRepository.delete(comment);
        Events.raise(new CommentDeletedEvent(comment.getPostId()));
    }
}