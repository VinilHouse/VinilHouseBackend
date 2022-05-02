package com.ssafy.happyhouse5.service.impl;

import static com.ssafy.happyhouse5.constant.BoardConst.*;

import com.ssafy.happyhouse5.dao.BoardDao;
import com.ssafy.happyhouse5.dto.board.Board;
import com.ssafy.happyhouse5.service.BoardService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class BoardServiceImpl implements BoardService {

    private final BoardDao boardDao;

    @Override
    @Transactional
    public void create(Board board) {
        boardDao.create(board);
    }

    @Override
    @Transactional
    public void update(String memberId, Board board) {
        Board find = getBoardIfExists(board.getId());
        if (!find.getMemberId().equals(memberId)) {
            throw new IllegalArgumentException(WRITER_ONLY_MODIFY);
        }
        boardDao.update(board);
    }

    @Override
    @Transactional
    public void delete(int id) {
        getBoardIfExists(id);
        boardDao.delete(id);
    }

    @Override
    public Board selectById(int id) {
        return boardDao.selectById(id);
    }

    @Override
    public List<Board> findAll() {
        return boardDao.selectAll();
    }

    @Override
    public List<Board> findByOption(BoardSearchOption boardSearchOption, String query) {
        switch (boardSearchOption) {
            case LIKE_TITLE:
                return boardDao.findLikeTitle(query);
            case LIKE_CONTENT:
                return boardDao.findLikeContent(query);
            case BY_MEMBER_ID:
                return boardDao.findByMemberId(query);
        }
        throw new RuntimeException();
    }

    private Board getBoardIfExists(int boardId) {
        Board board = boardDao.selectById(boardId);
        if (board == null) {
            throw new IllegalArgumentException(BOARD_NOT_FOUND);
        }
        return board;
    }
}
