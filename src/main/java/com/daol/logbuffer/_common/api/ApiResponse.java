package com.daol.logbuffer._common.api;

import com.daol.logbuffer._common.exception.ErrorCode;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class ApiResponse {

    public static <D> ResponseEntity<D> ok(D data) {
        return ResponseEntity.status(HttpStatus.OK).body(data);
    }

    public static <D> ResponseEntity<D> created(D data) {
        return ResponseEntity.status(HttpStatus.CREATED).body(data);
    }

    public static ResponseEntity<Void> noContent() {
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
    }

    public static ResponseEntity<FailResponse> error(ErrorCode errorCode, String message) {
        return ResponseEntity.status(errorCode.getStatus()).body(new FailResponse(errorCode.getCode(), message));
    }
}