package com.ssafy.happyhouse5.exception.member;

import static com.ssafy.happyhouse5.constant.MemberConst.MEMBER_LOCATION_ALIAS_DUPLICATE_MSG;
import static org.springframework.http.HttpStatus.BAD_REQUEST;

import com.ssafy.happyhouse5.exception.common.ApplicationException;

public class MemberLocationAliasDuplicateException extends ApplicationException {

    public MemberLocationAliasDuplicateException() {
        super(BAD_REQUEST, MEMBER_LOCATION_ALIAS_DUPLICATE_MSG);
    }
}
