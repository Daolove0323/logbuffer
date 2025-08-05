package com.daol.logbuffer.comment.comment.query;

import com.daol.logbuffer.comment.comment.command.GuestCommentAuthorId;
import com.daol.logbuffer.comment.comment.command.MemberCommentAuthorId;
import com.daol.logbuffer.post.command.PostId;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CommentDataRepository {

    Page<CommentResponse> findCommentsForAdmin(PostId postId, Pageable pageable);

    Page<CommentResponse> findCommentsForMember(PostId postId, MemberCommentAuthorId authorId, Pageable pageable);

    Page<CommentResponse> findCommentsForGuest(PostId postId, GuestCommentAuthorId authorId, Pageable pageable);

}