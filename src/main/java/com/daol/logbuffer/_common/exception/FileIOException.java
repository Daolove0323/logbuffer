package com.daol.logbuffer._common.exception;

public class FileIOException extends BusinessException {

    public FileIOException(String message) {
        super(message, ErrorCode.FILE_IO_ERROR);
    }
}
