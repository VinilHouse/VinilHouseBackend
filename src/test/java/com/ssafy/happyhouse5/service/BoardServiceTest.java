package com.ssafy.happyhouse5.service;

import static com.ssafy.happyhouse5.constants.BoardTestConst.*;
import static org.assertj.core.api.Assertions.*;

import com.ssafy.happyhouse5.dto.board.BoardRegisterDto;
import com.ssafy.happyhouse5.entity.Board;
import com.ssafy.happyhouse5.entity.Member;
import com.ssafy.happyhouse5.service.impl.BoardSearchOption;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
class BoardServiceTest {

    @PersistenceContext
    EntityManager em;

    @Autowired
    BoardService boardService;

    @Autowired
    MemberService memberService;

    @BeforeEach
    void before() {
        Member member1 = Member.builder()
            .ident(MEMBER_ID_1)
            .password(MEMBER_PASSWORD_1)
            .email(MEMBER_EMAIL_1)
            .build();

        Member member2 = Member.builder()
            .ident(MEMBER_ID_2)
            .password(MEMBER_PASSWORD_2)
            .email(MEMBER_EMAIL_2)
            .build();

        em.persist(member1);
        em.persist(member2);

        Board board1 = Board.builder()
            .title(BOARD_TITLE_1)
            .content(BOARD_CONTENT_1)
            .member(member1)
            .build();

        Board board2 = Board.builder()
            .title(BOARD_TITLE_2)
            .content(BOARD_CONTENT_2)
            .member(member2)
            .build();

        em.persist(board1);
        em.persist(board2);
    }

    @Test
    @DisplayName("단순 생성 후 개수 비교 테스트")
    void create() {
        String title = "new_TITLE";
        String content = "new_CONTENT";

        BoardRegisterDto dto = new BoardRegisterDto();
        dto.setTitle(title);
        dto.setContent(content);

        Member member = memberService.findMemberByIdent(MEMBER_ID_1);

        List<Board> before = boardService.findAll();
        assertThat(before).isNotNull();

        int beforeSize = before.size();
        boardService.create(member.getId(), dto);

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