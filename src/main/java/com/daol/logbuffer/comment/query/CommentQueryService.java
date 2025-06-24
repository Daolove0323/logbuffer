package com.daol.logbuffer.comment.query;

import com.daol.logbuffer._common.api.PageResponse;
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

    public PageResponse<CommentResponse> getComments(PostId postId, Pageable pageable) {
        Page<CommentResponse> comments = commentRepository.findCommentsForAdmin(postId, pageable);
        return PageResponse.of(comments.toList(), comments.getNumber(), comments.getTotalPages());
    }
}