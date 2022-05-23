package com.ssafy.happyhouse5.exception.comment;

import static com.ssafy.happyhouse5.constant.CommentConst.*;
import static org.springframework.http.HttpStatus.*;

import com.ssafy.happyhouse5.exception.common.ApplicationException;

public class CommentNotFoundException extends ApplicationException {

    public CommentNotFoundException() {
        super(BAD_REQUEST, COMMENT_NOT_FOUND_MSG);
    }
}
