package com.daol.logbuffer.comment.comment.application;

import com.daol.logbuffer.comment.comment.command.Comment;
import com.daol.logbuffer.comment.comment.command.CommentRepository;
import com.daol.logbuffer.comment.comment.command.GuestCommentAuthorId;
import com.daol.logbuffer.comment.comment.command.MemberCommentAuthorId;
import com.daol.logbuffer.comment.post.PostChecker;
import com.daol.logbuffer.member.auth.CurrentUser;
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
    public CommentCreationResponse createComment(CurrentUser user, PostId postId, CommentCreationRequest commentReq) {
        postChecker.verifyPostExists(postId);
        Comment comment;
        if (user.isGuest()) {
            comment = Comment.createByGuest(
                commentReq.content(), postId, new GuestCommentAuthorId(user.getGuestId()), commentReq.isHidden());
        } else {
            comment = Comment.createByMember(
                commentReq.content(), postId, new MemberCommentAuthorId(user.getMemberId()), commentReq.isHidden());
        }
        commentRepository.save(comment);
//        Events.raise(new CommentCreatedEvent(postId));
        return CommentCreationResponse.from(comment);
    }
}