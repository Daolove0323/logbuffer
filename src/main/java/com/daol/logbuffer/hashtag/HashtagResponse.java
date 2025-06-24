package com.daol.logbuffer.hashtag;

import java.util.UUID;

public record HashtagResponse(UUID hashtagId, String name) {

    public static HashtagResponse from(Hashtag hashtag) {
        return new HashtagResponse(hashtag.getId().getValue(), hashtag.getName());
    }
}