package com.ssafy.happyhouse5.exception.member;

import static com.ssafy.happyhouse5.constant.MemberConst.MEMBER_BAD_AUTH_MSG;
import static org.springframework.http.HttpStatus.UNAUTHORIZED;

import com.ssafy.happyhouse5.exception.common.ApplicationException;

public class MemberAuthException extends ApplicationException {

    public MemberAuthException() {
        super(UNAUTHORIZED, MEMBER_BAD_AUTH_MSG);
    }
}
