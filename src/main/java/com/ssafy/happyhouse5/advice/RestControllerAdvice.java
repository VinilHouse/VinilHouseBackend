package com.ssafy.happyhouse5.advice;

import static com.ssafy.happyhouse5.constant.CommonConst.*;
import static com.ssafy.happyhouse5.dto.common.Response.*;
import static org.springframework.http.HttpStatus.*;

import com.ssafy.happyhouse5.dto.common.Response;
import com.ssafy.happyhouse5.exception.common.ApplicationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.NoHandlerFoundException;

@RestController
@Slf4j
public class RestControllerAdvice {

    @ExceptionHandler(NoHandlerFoundException.class)
    public ResponseEntity<Response> handleNoHandlerFoundException(NoHandlerFoundException e) {
        log.warn("handleNoHandlerFoundException: {}", e.getMessage());
        return ResponseEntity
            .status(BAD_REQUEST)
            .body(fail(NO_HANDLER_MSG));
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<Response> handleHttpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException e) {
        log.error("handleHttpRequestMethodNotSupportedException: {}", e.getMessage());
        return ResponseEntity
            .status(BAD_REQUEST)
            .body(fail(BAD_REQUEST_MSG));
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<Response> handleMethodArgumentTypeMismatchException(MethodArgumentTypeMismatchException e) {
        log.warn("handleMethodArgumentTypeMismatchException: {}", e.getMessage());
        return ResponseEntity
            .status(BAD_REQUEST)
            .body(fail(BAD_REQUEST_MSG));
    }

    @ExceptionHandler(ApplicationException.class)
    public ResponseEntity<Response> handleApplicationException(ApplicationException e) {
        log.warn("handleApplicationException: {}",e.getMessage());
        return ResponseEntity
            .status(e.getStatus())
            .body(fail(e.getMessage()));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Response> handleInternalServerException(Exception e) {
        log.error("handleInternalServerException: {}", e.getMessage());
        return ResponseEntity
            .status(INTERNAL_SERVER_ERROR)
            .body(fail(e.getMessage()));
    }
}
