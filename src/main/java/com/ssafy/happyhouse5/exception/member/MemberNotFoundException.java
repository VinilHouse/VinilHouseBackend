package com.ssafy.happyhouse5.exception.member;

import static org.springframework.http.HttpStatus.NOT_FOUND;

import com.ssafy.happyhouse5.constant.MemberConst;
import com.ssafy.happyhouse5.exception.common.ApplicationException;

public class MemberNotFoundException extends ApplicationException {

    public MemberNotFoundException() {
        super(NOT_FOUND, MemberConst.MEMBER_NOT_FOUND);
    }
}
