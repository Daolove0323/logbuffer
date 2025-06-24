package com.daol.logbuffer._common.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ErrorCode {

    // Todo: 커스텀 에러코드 정의
    ENTITY_NOT_FOUND(HttpStatus.NOT_FOUND.value(), "-40400"),
    INVALID_REQUEST(HttpStatus.BAD_REQUEST.value(), "-40001"),
    UNAUTHORIZED(HttpStatus.UNAUTHORIZED.value(), "-40100"),
    UNAUTHENTICATED(HttpStatus.UNAUTHORIZED.value(), "-40100"),
    FILE_IO_ERROR(HttpStatus.INTERNAL_SERVER_ERROR.value(), "-50000");

    private final Integer status;
    private final String code;

    ErrorCode(int status, String code) {
        this.status = status;
        this.code = code;
    }
}