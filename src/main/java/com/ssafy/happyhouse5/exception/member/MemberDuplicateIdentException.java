package com.ssafy.happyhouse5.exception.member;

import static com.ssafy.happyhouse5.constant.MemberConst.*;
import static org.springframework.http.HttpStatus.*;

import com.ssafy.happyhouse5.exception.common.ApplicationException;

public class MemberDuplicateIdentException extends ApplicationException {

    public MemberDuplicateIdentException() {
        super(BAD_REQUEST, MEMBER_DUPLICATE_IDENT_MSG);
    }
}
