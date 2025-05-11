package com.daol.logbuffer._common.api;

import java.util.List;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class PageResponse<T> {

    private final List<T> data;
    private final Integer currentPage;
    private final Integer totalPages;

    public static <T> PageResponse<T> of(List<T> data, Integer currentPage, Integer totalPages) {
        return new PageResponse<>(data, currentPage, totalPages);
    }
}