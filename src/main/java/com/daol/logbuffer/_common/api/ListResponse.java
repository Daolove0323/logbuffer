package com.daol.logbuffer._common.api;

import java.util.List;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class ListResponse<T> {

    private final List<T> data;

    public static <T> ListResponse<T> of(List<T> data) {
        return new ListResponse<>(data);
    }
}