package com.ssafy.happyhouse5.service;

import com.ssafy.happyhouse5.dto.board.BoardRegisterDto;
import com.ssafy.happyhouse5.dto.board.BoardUpdateDto;
import com.ssafy.happyhouse5.entity.Board;
import com.ssafy.happyhouse5.service.impl.BoardSearchOption;
import java.util.List;

public interface BoardService {

    Long create(Long memberId, BoardRegisterDto boardRegisterDto);

    Long update(Long memberId, Long boardId, BoardUpdateDto boardUpdateDto);

    Long delete(Long id);

    Board selectById(Long id);

    List<Board> findAll();

    List<Board> findByOption(BoardSearchOption boardSearchOption, String query);
}
