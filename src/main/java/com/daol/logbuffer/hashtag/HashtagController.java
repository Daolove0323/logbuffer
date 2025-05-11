package com.daol.logbuffer.hashtag;

import com.daol.logbuffer._common.api.ApiResponse;
import com.daol.logbuffer._common.api.ListResponse;
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
    public ResponseEntity<HashtagResponse> createHashtag(
        @RequestBody HashtagRequest hashtagReq
    ) {
        return ApiResponse.created(hashtagService.createHashtag(hashtagReq));
    }

    @DeleteMapping("/{hashtagId}")
    public ResponseEntity<Void> deleteHashtag(
        @PathVariable UUID hashtagId
    ) {
        hashtagService.deleteHashtag(new HashtagId(hashtagId));
        return ApiResponse.noContent();
    }
}
