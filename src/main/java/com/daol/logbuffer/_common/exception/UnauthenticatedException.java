package com.daol.logbuffer._common.exception;

public class UnauthenticatedException extends BusinessException {

    public UnauthenticatedException(String message) {
        super(message, ErrorCode.UNAUTHENTICATED);
    }
}