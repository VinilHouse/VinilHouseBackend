package com.ssafy.happyhouse5.service.impl;

import com.ssafy.happyhouse5.dto.board.BoardRegisterDto;
import com.ssafy.happyhouse5.dto.board.BoardUpdateDto;
import com.ssafy.happyhouse5.entity.Board;
import com.ssafy.happyhouse5.entity.Member;
import com.ssafy.happyhouse5.exception.board.BoardNotFoundException;
import com.ssafy.happyhouse5.exception.member.MemberAuthException;
import com.ssafy.happyhouse5.exception.member.MemberNotFoundException;
import com.ssafy.happyhouse5.repository.BoardRepository;
import com.ssafy.happyhouse5.repository.MemberRepository;
import com.ssafy.happyhouse5.service.BoardService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class BoardServiceImpl implements BoardService {

    private final BoardRepository boardRepository;

    private final MemberRepository memberRepository;

    @Override
    @Transactional
    public Long create(Long memberId, BoardRegisterDto boardRegisterDto) {
        Member member = memberRepository.findById(memberId).orElseThrow(
            MemberNotFoundException::new);

        Board board = Board.builder()
            .title(boardRegisterDto.getTitle())
            .content(boardRegisterDto.getContent())
            .member(member)
            .build();

        return boardRepository.save(board).getId();
    }

    @Override
    @Transactional
    public Long update(Long memberId, Long boardId, BoardUpdateDto boardUpdateDto) {
        Board board = checkExistAndGetBoard(boardId);

        if (!board.getMember().getId().equals(memberId)) {
            throw new MemberAuthException();
        }
        board.setTitle(boardUpdateDto.getTitle());
        board.setContent(boardUpdateDto.getContent());
        return boardId;
    }

    @Override
    @Transactional
    public Long delete(Long id) {
        Board board = checkExistAndGetBoard(id);
        boardRepository.delete(board);
        return board.getId();
    }

    @Override
    public Board selectById(Long id) {
        return checkExistAndGetBoard(id);
    }

    @Override
    public List<Board> findAll() {
        return boardRepository.findAll();
    }

    @Override
    public List<Board> findByOption(BoardSearchOption boardSearchOption, String query) {
        switch (boardSearchOption) {
            case LIKE_TITLE:
                return boardRepository.findLikeTitle(query);
            case LIKE_CONTENT:
                return boardRepository.findLikeContent(query);
            case BY_MEMBER_ID:
                return boardRepository.findByMemberId(query);
        }
        throw new RuntimeException();
    }

    private Board checkExistAndGetBoard(Long id) {
        return boardRepository.findById(id).orElseThrow(BoardNotFoundException::new);
    }
}
