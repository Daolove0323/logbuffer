package com.daol.logbuffer.postlike.ui;

import com.daol.logbuffer._common.api.ApiResponse;
import com.daol.logbuffer.post.command.post.PostId;
import com.daol.logbuffer.postlike.application.PostLikeToggleService;
import com.daol.logbuffer.postlike.command.PostLikerId;
import com.daol.logbuffer.postlike.query.PostLikeQueryService;
import java.util.UUID;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/posts")
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
@CrossOrigin(origins = "*")
public class PostLikeController {

    private final PostLikeQueryService likeQueryService;
    private final PostLikeToggleService likeToggleService;

    @GetMapping("/{postId}/likes/{likerId}")
    public ResponseEntity<Boolean> isLiked(
        @PathVariable UUID postId,
        @PathVariable UUID likerId
    ) {
        return ApiResponse.ok(likeQueryService.getLike(
            new PostId(postId), new PostLikerId(likerId)));
    }

    @PostMapping("/{postId}/likes/{likerId}")
    public ResponseEntity<Void> toggleLike(
        @PathVariable UUID postId,
        @PathVariable UUID likerId
    ) {
        likeToggleService.toggleLike(new PostId(postId), new PostLikerId(likerId));
        return ApiResponse.noContent();
    }
}