package com.daol.logbuffer.post.query;

import com.daol.logbuffer._common.api.PageResponse;
import com.daol.logbuffer._common.event.Events;
import com.daol.logbuffer._common.event.PostViewedEvent;
import com.daol.logbuffer._common.exception.EntityNotFoundException;
import com.daol.logbuffer.post.command.PostId;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public class PostQueryService {

    private final PostDataRepository postRepository;

    @Transactional(readOnly = true)
    public PostDetailResponse getPostDetail(PostId postId) {
        PostDetailResponse res = postRepository.findDetailPost(postId).orElseThrow(
            () -> new EntityNotFoundException("해당 게시글을 찾을 수 없습니다."));
        Events.raise(new PostViewedEvent(postId));
        return res;
    }

    @Transactional(readOnly = true)
    public PageResponse<PostPreviewResponse> getPostsByFilter(PostFilter filter, Pageable pageable
    ) {
        Page<PostPreviewResponse> posts = postRepository.findPostsByFilter(
            pageable, filter.categoryId(), filter.hashtagId(), filter.keyword());
        return PageResponse.of(posts);
    }
}