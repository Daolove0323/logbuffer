package com.daol.logbuffer._common.api;

import java.util.List;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.data.domain.Page;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class PageResponse<T> {

    private final List<T> data;
    private final Integer currentPage;
    private final Integer totalPages;

    public static <T> PageResponse<T> of(Page<T> page) {
        return new PageResponse<>(page.toList(), page.getNumber(), page.getTotalPages());
    }
}