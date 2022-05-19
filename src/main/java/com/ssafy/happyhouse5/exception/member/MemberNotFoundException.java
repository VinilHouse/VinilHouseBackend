package com.ssafy.happyhouse5.exception.member;

import static com.ssafy.happyhouse5.constant.MemberConst.*;
import static org.springframework.http.HttpStatus.NOT_FOUND;

import com.ssafy.happyhouse5.constant.MemberConst;
import com.ssafy.happyhouse5.exception.common.ApplicationException;

public class MemberNotFoundException extends ApplicationException {

    public MemberNotFoundException() {
        super(NOT_FOUND, MEMBER_NOT_FOUND_MSG);
    }
}
