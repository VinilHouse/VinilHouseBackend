package com.ssafy.happyhouse5.exception.member;

import static com.ssafy.happyhouse5.constant.MemberConst.*;
import static org.springframework.http.HttpStatus.*;

import com.ssafy.happyhouse5.constant.MemberConst;
import com.ssafy.happyhouse5.exception.common.ApplicationException;
import org.springframework.http.HttpStatus;

public class MemberLocationConvertException extends ApplicationException {

    public MemberLocationConvertException() {
        super(NOT_FOUND, MEMBER_LOCATION_NO_RESULT_MSG);
    }
}
