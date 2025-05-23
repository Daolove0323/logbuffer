package com.daol.logbuffer.post.query;

import com.daol.logbuffer._common.api.PageResponse;
import com.daol.logbuffer.category.CategoryId;
import com.daol.logbuffer.hashtag.HashtagId;
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

    @Transactional
    public PostDetailResponse getPostDetail(PostId postId) {
        return postRepository.findDetailPost(postId).orElseThrow();
    }

    @Transactional(readOnly = true)
    public PageResponse<PostPreviewResponse> getAllPostsSortedByNewest(Pageable pageable) {
        Page<PostPreviewResponse> posts = postRepository.findPublishedPosts(pageable);
        return PageResponse.of(posts.stream().toList(), posts.getNumber(), posts.getTotalPages());
    }

    @Transactional(readOnly = true)
    public PageResponse<PostPreviewResponse> getPostsByCategory(CategoryId categoryId,
        Pageable pageable) {
        Page<PostPreviewResponse> posts = postRepository.findPublishedPostsByCategory(
            categoryId, pageable);
        return PageResponse.of(posts.stream().toList(), posts.getNumber(), posts.getTotalPages());
    }

    @Transactional(readOnly = true)
    public PageResponse<PostPreviewResponse> getPostsByHashtag(HashtagId hashtagId,
        Pageable pageable) {
        Page<PostPreviewResponse> posts = postRepository.findPublishedPostsByHashtag(
            hashtagId, pageable);
        return PageResponse.of(posts.stream().toList(), posts.getNumber(), posts.getTotalPages());
    }

    @Transactional(readOnly = true)
    public PageResponse<PostPreviewResponse> getPostsByTitleContaining(String title,
        Pageable pageable) {
        Page<PostPreviewResponse> posts = postRepository.findPublishedPostsByTitleContaining(title,
            pageable);
        return PageResponse.of(posts.stream().toList(), posts.getNumber(), posts.getTotalPages());
    }
}
