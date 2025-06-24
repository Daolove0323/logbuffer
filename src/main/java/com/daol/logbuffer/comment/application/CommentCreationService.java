package com.daol.logbuffer.comment.application;

import com.daol.logbuffer.comment.command.Comment;
import com.daol.logbuffer.comment.command.CommentAuthorId;
import com.daol.logbuffer.comment.command.CommentRepository;
import com.daol.logbuffer.post.command.PostId;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public class CommentCreationService {

    private final CommentRepository commentRepository;

    // Todo: PostId에 해당하는 Post가 존재하는지 확인하는 Policy 추가
    @Transactional
    public CommentCreationResponse createComment(CommentAuthorId authorId, PostId postId, CommentCreationRequest commentReq) {
        Comment comment = Comment.create(commentReq.content(), postId, authorId, commentReq.isHidden());
        commentRepository.save(comment);
        return CommentCreationResponse.from(comment);
    }
}