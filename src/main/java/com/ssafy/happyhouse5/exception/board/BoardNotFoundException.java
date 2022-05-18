package com.ssafy.happyhouse5.exception.board;

import static com.ssafy.happyhouse5.constant.BoardConst.*;
import static org.springframework.http.HttpStatus.*;

import com.ssafy.happyhouse5.exception.common.ApplicationException;
import org.springframework.http.HttpStatus;

public class BoardNotFoundException extends ApplicationException {

    public BoardNotFoundException() {
        super(NOT_FOUND, BOARD_NOT_FOUND);
    }
}
