package com.daol.logbuffer.post.ui;

import com.daol.logbuffer._common.api.ApiResponse;
import com.daol.logbuffer._common.api.PageDefault;
import com.daol.logbuffer._common.api.PageResponse;
import com.daol.logbuffer._common.argresolver.Auth;
import com.daol.logbuffer.member.auth.CurrentUser;
import com.daol.logbuffer.member.common.Grade;
import com.daol.logbuffer.post.application.PostCreationCommand;
import com.daol.logbuffer.post.application.PostCreationRequest;
import com.daol.logbuffer.post.application.PostCreationResponse;
import com.daol.logbuffer.post.application.PostCreationService;
import com.daol.logbuffer.post.application.PostDeletionCommand;
import com.daol.logbuffer.post.application.PostDeletionService;
import com.daol.logbuffer.post.application.PostUpdateCommand;
import com.daol.logbuffer.post.application.PostUpdateRequest;
import com.daol.logbuffer.post.application.PostUpdateResponse;
import com.daol.logbuffer.post.application.PostUpdateService;
import com.daol.logbuffer.post.command.PostId;
import com.daol.logbuffer.post.query.PostDetailResponse;
import com.daol.logbuffer.post.query.PostFilter;
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
    public ResponseEntity<PageResponse<PostPreviewResponse>> getPostsByFilter(
        @RequestParam(defaultValue = PageDefault.DEFAULT_PAGE) Integer page,
        @RequestParam(defaultValue = PageDefault.POST_PAGE_SIZE) Integer size,
        @RequestParam(required = false) UUID categoryId,
        @RequestParam(required = false) UUID hashtagId,
        @RequestParam(required = false) String keyword
    ) {
        PostFilter filter = new PostFilter(categoryId, hashtagId, keyword);
        return ApiResponse.ok(postQueryService.getPostsByFilter(filter, PageRequest.of(page, size)));
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
        @Auth(value = Grade.ADMIN) CurrentUser member
    ) {
        PostCreationCommand cmd = PostCreationCommand.from(postCreateReq, member.getMemberId());
        return ApiResponse.created(postCreationService.createPost(cmd));
    }

    @PutMapping("/{postId}")
    public ResponseEntity<PostUpdateResponse> updatePost(
        @PathVariable UUID postId,
        @RequestBody PostUpdateRequest postUpdateReq,
        @Auth(value = Grade.ADMIN) CurrentUser member
    ) {
        PostUpdateCommand cmd = PostUpdateCommand.from(postId, postUpdateReq, member.getMemberId());
        return ApiResponse.ok(postUpdateService.updatePost(cmd));
    }

    @DeleteMapping("/{postId}")
    public ResponseEntity<Void> deletePost(
        @PathVariable UUID postId,
        @Auth(value = Grade.ADMIN) CurrentUser member
    ) {
        PostDeletionCommand cmd = PostDeletionCommand.from(postId, member.getMemberId());
        postDeletionService.delete(cmd);
        return ApiResponse.noContent();
    }
}