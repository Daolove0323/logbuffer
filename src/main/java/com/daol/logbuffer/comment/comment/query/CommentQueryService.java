package com.daol.logbuffer.comment.comment.query;

import com.daol.logbuffer._common.api.PageResponse;
import com.daol.logbuffer._common.exception.InvalidRequestException;
import com.daol.logbuffer.comment.comment.command.GuestCommentAuthorId;
import com.daol.logbuffer.comment.comment.command.MemberCommentAuthorId;
import com.daol.logbuffer.member.auth.CurrentUser;
import com.daol.logbuffer.post.command.PostId;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public class CommentQueryService {

    private final CommentDataRepository commentRepository;

    public PageResponse<CommentResponse> getComments(CurrentUser user, PostId postId, Pageable pageable) {
        Page<CommentResponse> comments;
        if (user.isAdmin()) {
            comments = commentRepository.findCommentsForAdmin(postId, pageable);
        } else if (user.isMember()) {
            MemberCommentAuthorId id = new MemberCommentAuthorId(user.getMemberId());
            comments = commentRepository.findCommentsForMember(postId, id, pageable);
        } else if (user.isGuest()) {
            GuestCommentAuthorId id = new GuestCommentAuthorId(user.getGuestId());
            comments = commentRepository.findCommentsForGuest(postId, id, pageable);
        } else {
            throw new InvalidRequestException("해당하는 사용자 타입이 없습니다.");
        }
        return PageResponse.of(comments);
    }
}