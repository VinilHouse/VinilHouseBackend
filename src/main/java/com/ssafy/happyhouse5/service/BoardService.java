package com.ssafy.happyhouse5.service;

import com.ssafy.happyhouse5.dto.board.Board;
import com.ssafy.happyhouse5.dto.member.Member;
import com.ssafy.happyhouse5.service.impl.BoardSearchOption;
import java.util.List;

public interface BoardService {

    void create(Board board);

    void update(Member memberId, Board board);

    Board selectById(int id);

    List<Board> findAll();

    List<Board> findByOption(BoardSearchOption boardSearchOption, String query);
}
