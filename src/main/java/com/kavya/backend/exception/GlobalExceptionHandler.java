package com.kavya.backend.exception;

import com.kavya.backend.util.CommonUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ExistDataException.class)
    public ResponseEntity<?> handleExistDatabaseException(ExistDataException ex) {
        log.error("GlobalExceptionHandler : handleExistDatabaseException() : {}", ex.getMessage());
        return CommonUtil.createErrorResponse(ex.getMessage(), HttpStatus.CONFLICT);
    }

    @ExceptionHandler(JwtTokenExpiredException.class)
    public ResponseEntity<?> handleJwtTokenExpiredException(JwtTokenExpiredException ex) {
        log.error("GlobalExceptionHandler : handleJwtTokenExpiredException() : {}", ex.getMessage());
        return CommonUtil.createErrorResponse(ex.getMessage(), HttpStatus.CONFLICT);
    }
}
