package com.daol.logbuffer.comment.query;

import com.daol.logbuffer.post.command.PostId;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

// Todo: QueryDSL 사용
// Todo: 사용자의 권한에 따라 댓글 숨김 처리
public interface CommentDataRepository {

    Page<CommentResponse> findCommentsForAdmin(PostId postId, Pageable pageable);

    Page<CommentResponse> findCommentsForMember(PostId postId, Pageable pageable);
}