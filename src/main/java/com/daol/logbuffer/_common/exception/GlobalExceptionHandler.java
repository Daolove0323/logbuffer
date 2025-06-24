package com.daol.logbuffer._common.exception;

import com.daol.logbuffer._common.api.ApiResponse;
import com.daol.logbuffer._common.api.FailResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;

public class GlobalExceptionHandler {

    // Todo: ValidationException 핸들링
    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<FailResponse> handleBusinessException(BusinessException e) {
        return ApiResponse.error(e.getErrorCode(), e.getMessage());
    }
}