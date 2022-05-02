package com.ssafy.happyhouse5.service;

import static com.ssafy.happyhouse5.constfortest.BoardTestConst.*;
import static org.assertj.core.api.Assertions.*;

import com.ssafy.happyhouse5.dao.BoardDao;
import com.ssafy.happyhouse5.dao.MemberDao;
import com.ssafy.happyhouse5.dto.board.Board;
import com.ssafy.happyhouse5.dto.member.Member;
import com.ssafy.happyhouse5.service.impl.BoardSearchOption;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
class BoardServiceTest {

    @Autowired
    BoardService boardService;

    @Autowired
    BoardDao boardDao;

    @Autowired
    MemberDao memberDao;

    @BeforeEach
    void before() {
        Member member1 = Member.builder()
            .id(MEMBER_ID_1)
            .password(MEMBER_PASSWORD_1)
            .email(MEMBER_EMAIL_1)
            .build();

        Member member2 = Member.builder()
            .id(MEMBER_ID_2)
            .password(MEMBER_PASSWORD_2)
            .email(MEMBER_EMAIL_2)
            .build();

        memberDao.register(member1);
        memberDao.register(member2);

        Board board1 = Board.builder()
            .title(BOARD_TITLE_1)
            .content(BOARD_CONTENT_1)
            .memberId(MEMBER_ID_1)
            .build();

        Board board2 = Board.builder()
            .title(BOARD_TITLE_2)
            .content(BOARD_CONTENT_2)
            .memberId(MEMBER_ID_2)
            .build();

        boardDao.create(board1);
        boardDao.create(board2);
    }

    @Test
    @DisplayName("단순 생성 후 개수 비교 테스트")
    void create() {
        String title = "new_TITLE";
        String content = "new_CONTENT";

        Board board = Board.builder()
            .title(title)
            .content(content)
            .memberId(MEMBER_ID_1)
            .build();

        List<Board> before = boardService.findAll();
        assertThat(before).isNotNull();

        int beforeSize = before.size();
        boardService.create(board);

        List<Board> after = boardService.findAll();
        assertThat(after).isNotNull();
        int afterSize = after.size();
        assertThat(afterSize - beforeSize).isEqualTo(1);
    }

    @Test
    @DisplayName("search option: LIKE_TITLE 테스트")
    void findByOptionLikeContent() {
        BoardSearchOption option = BoardSearchOption.LIKE_TITLE;
        List<Board> findBoards = boardService.findByOption(option, BOARD_COMMON_TITLE);

        assertThat(findBoards).isNotNull();
        assertThat(findBoards.size()).isEqualTo(2);
    }

    @Test
    @DisplayName("search option: LIKE_CONTENT 테스트")
    void findByOptionLikeTitle() {
        BoardSearchOption option = BoardSearchOption.LIKE_CONTENT;
        List<Board> findBoards = boardService.findByOption(option, BOARD_COMMON_CONTENT);

        assertThat(findBoards).isNotNull();
        assertThat(findBoards.size()).isEqualTo(2);
    }

    @Test
    @DisplayName("search option: BY_MEMBER_ID 테스트")
    void findByOptionByMemberId() {
        BoardSearchOption option = BoardSearchOption.BY_MEMBER_ID;
        List<Board> findBoards1 = boardService.findByOption(option, MEMBER_ID_1);
        assertThat(findBoards1).isNotNull();
        assertThat(findBoards1.size()).isEqualTo(1);

        List<Board> findBoards2 = boardService.findByOption(option, MEMBER_ID_2);
        assertThat(findBoards2).isNotNull();
        assertThat(findBoards2.size()).isEqualTo(1);
    }
}