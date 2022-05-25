package com.ssafy.happyhouse5.exception.member;

import static com.ssafy.happyhouse5.constant.MemberConst.*;
import static org.springframework.http.HttpStatus.*;

import com.ssafy.happyhouse5.constant.MemberConst;
import com.ssafy.happyhouse5.exception.common.ApplicationException;
import org.springframework.http.HttpStatus;

public class MemberLocationNotFoundException extends ApplicationException {

    public MemberLocationNotFoundException() {
        super(NOT_FOUND, MEMBER_LOCATION_NOT_FOUND_MSG);
    }
}
