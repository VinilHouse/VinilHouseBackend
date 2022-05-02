package com.ssafy.happyhouse5.constant;

public interface BoardConst {
    String WRITER_ONLY_MODIFY = "only writer modify board.";
    String BOARD_NOT_FOUND = "not exists board. (by boardId)";
    String NOT_ALLOWED_FIND_QUERY = "not allowed query key. allow = {title, content, member}";
    String INVALID_SIZE_OF_QUERY = "require only one or zero option";
}
