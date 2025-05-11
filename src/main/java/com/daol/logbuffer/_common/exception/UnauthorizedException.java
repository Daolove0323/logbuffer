package com.daol.logbuffer._common.exception;

public class UnauthorizedException extends BusinessException {

    public UnauthorizedException(String message) {
        super(message, ErrorCode.UNAUTHORIZED);
    }
}