package com.daol.logbuffer.category;

import java.util.UUID;

public record CategoryResponse(UUID categoryId, String name) {

    public static CategoryResponse from(Category category) {
        return new CategoryResponse(category.getId().getValue(), category.getName());
    }
}