package com.daol.logbuffer.post.ui;

import com.daol.logbuffer._common.api.ApiResponse;
import com.daol.logbuffer._common.api.PageDefault;
import com.daol.logbuffer._common.api.PageResponse;
import com.daol.logbuffer._common.argresolver.Auth;
import com.daol.logbuffer.category.CategoryId;
import com.daol.logbuffer.hashtag.HashtagId;
import com.daol.logbuffer.member.auth.AuthMember;
import com.daol.logbuffer.member.common.Grade;
import com.daol.logbuffer.post.application.PostCreationRequest;
import com.daol.logbuffer.post.application.PostCreationResponse;
import com.daol.logbuffer.post.application.PostCreationService;
import com.daol.logbuffer.post.application.PostDeletionService;
import com.daol.logbuffer.post.application.PostUpdateRequest;
import com.daol.logbuffer.post.application.PostUpdateResponse;
import com.daol.logbuffer.post.application.PostUpdateService;
import com.daol.logbuffer.post.command.PostAuthorId;
import com.daol.logbuffer.post.command.PostId;
import com.daol.logbuffer.post.query.PostDetailResponse;
import com.daol.logbuffer.post.query.PostPreviewResponse;
import com.daol.logbuffer.post.query.PostQueryService;
import java.util.Optional;
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
    public ResponseEntity<PageResponse<PostPreviewResponse>> getPostsByFilter(
        @RequestParam(defaultValue = PageDefault.DEFAULT_PAGE) Integer page,
        @RequestParam(defaultValue = PageDefault.POST_PAGE_SIZE) Integer size,
        @RequestParam(required = false) UUID categoryId,
        @RequestParam(required = false) UUID hashtagId,
        @RequestParam(required = false) String keyword
    ) {
        Optional<CategoryId> nullableCategoryId = Optional.ofNullable(categoryId).map(CategoryId::new);
        Optional<HashtagId> nullableHashtagId = Optional.ofNullable(hashtagId).map(HashtagId::new);
        Optional<String> nullableKeyword = Optional.ofNullable(keyword);
        return ApiResponse.ok(postQueryService.getPostsByFilter(
            nullableCategoryId, nullableHashtagId, nullableKeyword, PageRequest.of(page, size)));
    }

    @GetMapping("/{postId}")
    public ResponseEntity<PostDetailResponse> getDetailPost(
        @PathVariable UUID postId
    ) {
        return ApiResponse.ok(postQueryService.getPostDetail(new PostId(postId)));
    }

    @PostMapping
    public ResponseEntity<PostCreationResponse> createPost(
        @RequestBody PostCreationRequest postCreateReq,
        @Auth(value = Grade.ADMIN) AuthMember member
    ) {
        return ApiResponse.created(
            postCreationService.createPost(new PostAuthorId(member.memberId()), postCreateReq));
    }

    @PutMapping("/{postId}")
    public ResponseEntity<PostUpdateResponse> updatePost(
        @PathVariable UUID postId,
        @RequestBody PostUpdateRequest postUpdateReq,
        @Auth(value = Grade.ADMIN) AuthMember member
    ) {
        return ApiResponse.ok(
            postUpdateService.updatePost(
                new PostAuthorId(member.memberId()), new PostId(postId), postUpdateReq));
    }

    @DeleteMapping("/{postId}")
    public ResponseEntity<Void> deletePost(
        @PathVariable UUID postId,
        @Auth(value = Grade.ADMIN) AuthMember member
    ) {
        postDeletionService.delete(new PostAuthorId(member.memberId()), new PostId(postId));
        return ApiResponse.noContent();
    }
}