package com.daol.logbuffer.comment.comment.application;

import com.daol.logbuffer._common.exception.EntityNotFoundException;
import com.daol.logbuffer.comment.comment.command.Comment;
import com.daol.logbuffer.comment.comment.command.CommentId;
import com.daol.logbuffer.comment.comment.command.CommentRepository;
import com.daol.logbuffer.comment.comment.command.MemberCommentAuthorId;
import com.daol.logbuffer.member.auth.CurrentUser;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public class CommentUpdateService {

    private final CommentRepository commentRepository;

    @Transactional
    public CommentUpdateResponse updateComment(CurrentUser user, CommentId commentId, CommentUpdateRequest commentReq) {
        Comment comment = commentRepository.findById(commentId)
            .orElseThrow(() -> new EntityNotFoundException("ID에 해당하는 댓글을 찾을 수 없습니다."));
        comment.verifyAuthor(new MemberCommentAuthorId(user.getMemberId()));
        comment.changeContent(commentReq.content());
        comment.changeVisibility(commentReq.isHidden());
        return CommentUpdateResponse.from(comment);
    }
}