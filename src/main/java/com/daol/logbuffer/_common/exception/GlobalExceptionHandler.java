package com.daol.logbuffer._common.exception;

import com.daol.logbuffer._common.api.ApiResponse;
import com.daol.logbuffer._common.api.FailResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;

public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<FailResponse> handleValidationException(MethodArgumentNotValidException e) {
        String errorMessage = e.getBindingResult().getFieldError().getDefaultMessage();
        return ApiResponse.error(ErrorCode.ARGUMENT_NOT_VALID, errorMessage);
    }

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<FailResponse> handleBusinessException(BusinessException e) {
        return ApiResponse.error(e.getErrorCode(), e.getMessage());
    }
}