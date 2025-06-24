package com.daol.logbuffer._common.exception;

public class InvalidRequestException extends BusinessException {

    public InvalidRequestException(String message) {
        super(message, ErrorCode.INVALID_REQUEST);
    }
}