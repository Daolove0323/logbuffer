package com.daol.logbuffer._common.api;

import lombok.Getter;

@Getter
public class FailResponse {

    private final String code;
    private final String message;

    public FailResponse(String code, String message) {
        this.code = code;
        this.message = message;
    }
}