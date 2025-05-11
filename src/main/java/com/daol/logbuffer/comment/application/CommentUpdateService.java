package com.daol.logbuffer.comment.application;

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
public class CommentUpdateService {

    private final CommentRepository commentRepository;

    @Transactional
    public CommentUpdateResponse updateContent(CommentAuthorId authorId, CommentId commentId,
        CommentUpdateRequest commentReq) {
        Comment comment = commentRepository.findById(commentId).orElseThrow();
        comment.verifyAuthor(authorId);
        comment.changeContent(commentReq.content());
        if (commentReq.isHidden()) {
            comment.hide();
        } else {
            comment.publish();
        }
        return CommentUpdateResponse.from(comment);
    }
}