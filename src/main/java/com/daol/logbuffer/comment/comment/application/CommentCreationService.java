package com.daol.logbuffer.comment.comment.application;

import com.daol.logbuffer.comment.comment.command.Comment;
import com.daol.logbuffer.comment.comment.command.CommentAuthorId;
import com.daol.logbuffer.comment.comment.command.CommentRepository;
import com.daol.logbuffer.comment.post.PostChecker;
import com.daol.logbuffer.post.command.PostId;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public class CommentCreationService {

    private final CommentRepository commentRepository;
    private final PostChecker postChecker;

    @Transactional
    public CommentCreationResponse createComment(CommentAuthorId authorId, PostId postId, CommentCreationRequest commentReq) {
        postChecker.verifyPostExists(postId);
        Comment comment = Comment.create(commentReq.content(), postId, authorId, commentReq.isHidden());
        commentRepository.save(comment);
        return CommentCreationResponse.from(comment);
    }
}