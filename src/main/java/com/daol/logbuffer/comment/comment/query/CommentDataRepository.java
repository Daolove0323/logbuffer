package com.daol.logbuffer.comment.comment.query;

import com.daol.logbuffer.post.command.PostId;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CommentDataRepository {

    Page<CommentResponse> findCommentsForAdmin(PostId postId, Pageable pageable);

    Page<CommentResponse> findCommentsForMember(PostId postId, Pageable pageable);
}