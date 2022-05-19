package com.ssafy.happyhouse5.exception.house;

import static com.ssafy.happyhouse5.constant.HouseConst.*;
import static org.springframework.http.HttpStatus.*;

import com.ssafy.happyhouse5.exception.common.ApplicationException;

public class HouseInfoNotFoundException extends ApplicationException {

    public HouseInfoNotFoundException() {
        super(NOT_FOUND, HOUSE_INFO_NOT_FOUND_MSG);
    }
}
