package com.daol.logbuffer.post.ui;

import com.daol.logbuffer._common.api.ApiResponse;
import com.daol.logbuffer._common.api.PageDefault;
import com.daol.logbuffer._common.api.PageResponse;
import com.daol.logbuffer.category.CategoryId;
import com.daol.logbuffer.hashtag.HashtagId;
import com.daol.logbuffer.post.application.PostCreationRequest;
import com.daol.logbuffer.post.application.PostCreationResponse;
import com.daol.logbuffer.post.application.PostCreationService;
import com.daol.logbuffer.post.application.PostDeletionService;
import com.daol.logbuffer.post.application.PostUpdateRequest;
import com.daol.logbuffer.post.application.PostUpdateResponse;
import com.daol.logbuffer.post.application.PostUpdateService;
import com.daol.logbuffer.post.command.post.PostAuthorId;
import com.daol.logbuffer.post.command.post.PostId;
import com.daol.logbuffer.post.query.PostDetailResponse;
import com.daol.logbuffer.post.query.PostPreviewResponse;
import com.daol.logbuffer.post.query.PostQueryService;
import java.util.UUID;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
@RequestMapping("/api/posts")
public class PostController {

    private final PostCreationService postCreationService;
    private final PostUpdateService postUpdateService;
    private final PostDeletionService postDeletionService;
    private final PostQueryService postQueryService;

    @GetMapping
    public ResponseEntity<PageResponse<PostPreviewResponse>> getAllPosts(
        @RequestParam(defaultValue = PageDefault.PAGE) Integer page,
        @RequestParam(defaultValue = PageDefault.PAGE_SIZE) Integer size
    ) {
        return ApiResponse.ok(
            postQueryService.getAllPostsSortedByNewest(PageRequest.of(page, size)));
    }

    @GetMapping("/{postId}")
    public ResponseEntity<PostDetailResponse> getPostById(
        @PathVariable UUID postId
    ) {
        return ApiResponse.ok(postQueryService.getPostDetail(new PostId(postId)));
    }

    @GetMapping("/category/{categoryId}")
    public ResponseEntity<PageResponse<PostPreviewResponse>> getPostsByCategory(
        @PathVariable UUID categoryId,
        @RequestParam(defaultValue = PageDefault.PAGE) Integer page,
        @RequestParam(defaultValue = PageDefault.PAGE_SIZE) Integer size
    ) {
        return ApiResponse.ok(
            postQueryService.getPostsByCategory(new CategoryId(categoryId), PageRequest.of(page, size)));
    }

    @GetMapping("/hashtag/{hashtagId}")
    public ResponseEntity<PageResponse<PostPreviewResponse>> getPostsByHashtag(
        @PathVariable UUID hashtagId,
        @RequestParam(defaultValue = PageDefault.PAGE) Integer page,
        @RequestParam(defaultValue = PageDefault.PAGE_SIZE) Integer size
    ) {
        return ApiResponse.ok(
            postQueryService.getPostsByHashtag(new HashtagId(hashtagId), PageRequest.of(page, size)));
    }

    @PostMapping
    public ResponseEntity<PostCreationResponse> createPost(
        @RequestBody PostCreationRequest postCreateReq
    ) {
        return ApiResponse.created(
            postCreationService.createPost(PostAuthorId.generate(), postCreateReq));
    }

    @PutMapping("/{postId}")
    public ResponseEntity<PostUpdateResponse> updatePost(
        @PathVariable UUID postId,
        @RequestBody PostUpdateRequest postUpdateReq
    ) {
        return ApiResponse.ok(
            postUpdateService.updatePost(PostAuthorId.generate(), new PostId(postId),
                postUpdateReq));
    }

    @DeleteMapping("/{postId}")
    public ResponseEntity<Void> deletePost(
        @PathVariable UUID postId
    ) {
        postDeletionService.delete(PostAuthorId.generate(), new PostId(postId));
        return ApiResponse.noContent();
    }
}
