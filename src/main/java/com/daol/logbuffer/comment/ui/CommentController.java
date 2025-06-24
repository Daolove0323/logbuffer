package com.daol.logbuffer.comment.ui;

import com.daol.logbuffer._common.api.ApiResponse;
import com.daol.logbuffer._common.api.PageDefault;
import com.daol.logbuffer._common.api.PageResponse;
import com.daol.logbuffer._common.argresolver.Auth;
import com.daol.logbuffer.comment.application.CommentCreationRequest;
import com.daol.logbuffer.comment.application.CommentCreationResponse;
import com.daol.logbuffer.comment.application.CommentCreationService;
import com.daol.logbuffer.comment.application.CommentDeletionService;
import com.daol.logbuffer.comment.application.CommentUpdateRequest;
import com.daol.logbuffer.comment.application.CommentUpdateResponse;
import com.daol.logbuffer.comment.application.CommentUpdateService;
import com.daol.logbuffer.comment.command.CommentAuthorId;
import com.daol.logbuffer.comment.command.CommentId;
import com.daol.logbuffer.comment.query.CommentQueryService;
import com.daol.logbuffer.comment.query.CommentResponse;
import com.daol.logbuffer.member.auth.AuthMember;
import com.daol.logbuffer.member.common.Grade;
import com.daol.logbuffer.post.command.PostId;
import jakarta.validation.Valid;
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
@RequestMapping("/api")
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public class CommentController {

    private final CommentCreationService commentCreationService;
    private final CommentUpdateService commentUpdateService;
    private final CommentDeletionService commentDeletionService;
    private final CommentQueryService commentQueryService;

    @GetMapping("/posts/{postId}/comments")
    public ResponseEntity<PageResponse<CommentResponse>> getComments(
        @RequestParam(defaultValue = PageDefault.DEFAULT_PAGE) Integer page,
        @RequestParam(defaultValue = PageDefault.COMMENT_PAGE_SIZE) Integer size,
        @PathVariable UUID postId
    ) {
        return ApiResponse.ok(
            commentQueryService.getComments(new PostId(postId), PageRequest.of(page, size)));
    }

    @PostMapping("/posts/{postId}/comments")
    public ResponseEntity<CommentCreationResponse> createComment(
        @PathVariable UUID postId,
        @Auth(Grade.NORMAL) AuthMember member,
        @RequestBody @Valid CommentCreationRequest commentReq
    ) {
        return ApiResponse.created(
            commentCreationService.createComment(new CommentAuthorId(member.memberId()), new PostId(postId), commentReq)
        );
    }

    @PutMapping("/comments/{commentId}")
    public ResponseEntity<CommentUpdateResponse> updateComment(
        @PathVariable UUID commentId,
        @Auth(Grade.NORMAL) AuthMember member,
        @RequestBody @Valid CommentUpdateRequest commentReq
    ) {
        return ApiResponse.ok(
            commentUpdateService.updateContent(new CommentAuthorId(member.memberId()), new CommentId(commentId), commentReq)
        );
    }

    @DeleteMapping("/comments/{commentId}")
    public ResponseEntity<Void> deleteComment(
        @Auth(Grade.NORMAL) AuthMember member,
        @PathVariable UUID commentId
    ) {
        commentDeletionService.deleteComment(new CommentAuthorId(member.memberId()), new CommentId(commentId));
        return ApiResponse.noContent();
    }
}