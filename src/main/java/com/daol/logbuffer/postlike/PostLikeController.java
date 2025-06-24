package com.daol.logbuffer.postlike;

import com.daol.logbuffer._common.api.ApiResponse;
import com.daol.logbuffer._common.argresolver.Auth;
import com.daol.logbuffer.member.auth.AuthMember;
import com.daol.logbuffer.member.common.Grade;
import com.daol.logbuffer.post.command.PostId;
import java.util.UUID;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/posts")
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public class PostLikeController {

    private final PostLikeQueryService likeQueryService;
    private final PostLikeToggleService likeToggleService;

    @GetMapping("/{postId}/likes")
    public ResponseEntity<Boolean> isLiked(
        @PathVariable UUID postId,
        @Auth(Grade.GUEST) AuthMember member
    ) {
        return ApiResponse.ok(likeQueryService.getLike(
            new PostId(postId), new PostLikerId(member.memberId())));
    }

    @PostMapping("/{postId}/likes")
    public ResponseEntity<Void> toggleLike(
        @PathVariable UUID postId,
        @Auth(Grade.GUEST) AuthMember member
    ) {
        likeToggleService.toggleLike(new PostId(postId), new PostLikerId(member.memberId()));
        return ApiResponse.noContent();
    }
}