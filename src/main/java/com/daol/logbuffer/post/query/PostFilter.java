package com.daol.logbuffer.post.query;

import com.daol.logbuffer.category.CategoryId;
import com.daol.logbuffer.hashtag.HashtagId;
import java.util.UUID;

public record PostFilter(CategoryId categoryId, HashtagId hashtagId, String keyword) {

    public PostFilter(UUID categoryId, UUID hashtagId, String keyword) {
        this(
            categoryId != null ? new CategoryId(categoryId) : null,
            hashtagId != null ? new HashtagId(hashtagId) : null,
            keyword
        );
    }
}