package com.ssafy.happyhouse5.advice;

import com.ssafy.happyhouse5.dto.common.Response;
import com.ssafy.happyhouse5.exception.common.ApplicationException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RestControllerAdvice {

    @ExceptionHandler(ApplicationException.class)
    public ResponseEntity<Response> handleApplicationException(ApplicationException e) {
        return ResponseEntity
            .status(e.getStatus())
            .body(Response.fail(e.getMessage()));
    }
}
