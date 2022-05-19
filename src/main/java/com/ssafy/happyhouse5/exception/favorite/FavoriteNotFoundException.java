package com.ssafy.happyhouse5.exception.favorite;

import static com.ssafy.happyhouse5.constant.FavoriteConst.*;
import static org.springframework.http.HttpStatus.*;

import com.ssafy.happyhouse5.exception.common.ApplicationException;

public class FavoriteNotFoundException extends ApplicationException {

    public FavoriteNotFoundException() {
        super(NOT_FOUND, FAVORITE_NOT_FOUND_MSG);
    }
}
