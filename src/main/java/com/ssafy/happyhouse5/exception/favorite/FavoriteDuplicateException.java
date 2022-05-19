package com.ssafy.happyhouse5.exception.favorite;

import static com.ssafy.happyhouse5.constant.FavoriteConst.*;
import static org.springframework.http.HttpStatus.*;

import com.ssafy.happyhouse5.exception.common.ApplicationException;

public class FavoriteDuplicateException extends ApplicationException {

    public FavoriteDuplicateException() {
        super(CONFLICT, FAVORITE_DUPLICATE_MSG);
    }
}
