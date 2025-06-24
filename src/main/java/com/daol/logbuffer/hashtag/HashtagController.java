package com.daol.logbuffer.hashtag;

import com.daol.logbuffer._common.api.ApiResponse;
import com.daol.logbuffer._common.api.ListResponse;
import com.daol.logbuffer._common.argresolver.Auth;
import com.daol.logbuffer.member.auth.AuthMember;
import com.daol.logbuffer.member.common.Grade;
import jakarta.validation.Valid;
import java.util.UUID;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/hashtags")
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public class HashtagController {

    private final HashtagService hashtagService;

    @GetMapping
    public ResponseEntity<ListResponse<HashtagResponse>> getHashtags() {
        return ApiResponse.ok(hashtagService.getAllHashtags());
    }

    @PostMapping
    public ResponseEntity<HashtagResponse> getOrCreateHashtag(
        @RequestBody @Valid HashtagRequest hashtagReq
    ) {
        return ApiResponse.created(hashtagService.getOrCreateHashtag(hashtagReq));
    }

    @DeleteMapping("/{hashtagId}")
    public ResponseEntity<Void> deleteHashtag(
        @PathVariable UUID hashtagId,
        @Auth(Grade.ADMIN) AuthMember member
    ) {
        hashtagService.deleteHashtag(new HashtagId(hashtagId));
        return ApiResponse.noContent();
    }
}