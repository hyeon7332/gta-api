package com.gta.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.gta.dto.ErrorResponse;

/**
 * 전역 예외 처리
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<ErrorResponse> handleBusinessException(BusinessException e)
    {
        ErrorResponse response = new ErrorResponse();
        response.setStatus(HttpStatus.BAD_REQUEST.value());
        response.setMessage(e.getMessage());
        response.setExpose(true);

        return ResponseEntity.badRequest().body(response);
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ErrorResponse> handleRuntimeException(RuntimeException e)
    {
        ErrorResponse response = new ErrorResponse();
        response.setStatus(HttpStatus.BAD_REQUEST.value());
        response.setMessage("요청 처리 중 오류가 발생했습니다.");
        response.setExpose(false);

        return ResponseEntity.badRequest().body(response);
    }

    @ExceptionHandler(UnauthorizedException.class)
    public ResponseEntity<ErrorResponse> handleUnauthorizedException(UnauthorizedException e)
    {
        ErrorResponse response = new ErrorResponse();
        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        response.setMessage(e.getMessage());
        response.setExpose(false);

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
    }

    @ExceptionHandler(ForbiddenException.class)
    public ResponseEntity<ErrorResponse> handleForbiddenException(ForbiddenException e)
    {
        ErrorResponse response = new ErrorResponse();
        response.setStatus(HttpStatus.FORBIDDEN.value());
        response.setMessage(e.getMessage());
        response.setExpose(false);

        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(response);
    }
}
