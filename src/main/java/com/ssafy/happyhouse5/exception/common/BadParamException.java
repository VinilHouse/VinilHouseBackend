package com.ssafy.happyhouse5.exception.common;

import static com.ssafy.happyhouse5.constant.CommonConst.*;
import static org.springframework.http.HttpStatus.*;

public class BadParamException extends ApplicationException{

    public BadParamException() {
        super(BAD_REQUEST, BAD_REQUEST_MSG);
    }
}
