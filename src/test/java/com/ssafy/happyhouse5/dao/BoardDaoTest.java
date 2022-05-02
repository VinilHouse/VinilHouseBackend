package com.ssafy.happyhouse5.dao;

import static com.ssafy.happyhouse5.constfortest.BoardTestConst.*;
import static org.assertj.core.api.Assertions.*;

import com.ssafy.happyhouse5.dto.board.Board;
import com.ssafy.happyhouse5.dto.member.Member;
import java.util.List;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
class BoardDaoTest {

    @Autowired
    MemberDao memberDao;

    @Autowired
    BoardDao boardDao;

    // almost test methods depend on this @BeforeEach
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
    @DisplayName("생성 후 게시판의 개수 변경 (1 -> 2) 테스트")
    void create() {
        String TITLE = "temp1";
        String CONTENT = "content1";

        List<Board> before = boardDao.findByMemberId(MEMBER_ID_1);

        Board board = Board.builder()
            .title(TITLE)
            .content(CONTENT)
            .memberId(MEMBER_ID_1)
            .build();

        boardDao.create(board);
        List<Board> after = boardDao.findByMemberId(MEMBER_ID_1);

        int beforeSize = before.size();
        int afterSize = after.size();
        assertThat(afterSize - beforeSize).isEqualTo(1);
    }

    @Test
    @DisplayName("컨텐츠만 업데이트 확인")
    void updateContent() {
        List<Board> findBoards = boardDao.findByMemberId(MEMBER_ID_1);
        Board board = findBoards.get(0);

        String newContent = "NEW_CONTENT";

        Board forUpdate = Board.builder()
            .id(board.getId())
            .content(newContent)
            .build();

        boardDao.update(forUpdate);

        Board afterUpdate = boardDao.selectById(board.getId());
        assertThat(afterUpdate).isNotNull();

        assertThat(afterUpdate.getId()).isEqualTo(board.getId());
        assertThat(afterUpdate.getTitle()).isEqualTo(board.getTitle());
        assertThat(afterUpdate.getContent()).isEqualTo(newContent);
        assertThat(afterUpdate.getMemberId()).isEqualTo(board.getMemberId());
    }

    @Test
    @DisplayName("타이틀만 업데이트 확인")
    void updateTitle() {
        List<Board> findBoards = boardDao.findByMemberId(MEMBER_ID_1);
        Board board = findBoards.get(0);

        String newTitle = "NEW_TITLE";

        Board forUpdate = Board.builder()
            .id(board.getId())
            .title(newTitle)
            .build();

        boardDao.update(forUpdate);

        Board afterUpdate = boardDao.selectById(board.getId());
        assertThat(afterUpdate).isNotNull();

        assertThat(afterUpdate.getId()).isEqualTo(board.getId());
        assertThat(afterUpdate.getTitle()).isEqualTo(newTitle);
        assertThat(afterUpdate.getContent()).isEqualTo(board.getContent());
        assertThat(afterUpdate.getMemberId()).isEqualTo(board.getMemberId());
    }

    @Test
    @DisplayName("타이틀, 컨텐트 업데이트 확인")
    void updateTitleAndContent() {
        List<Board> findBoards = boardDao.findByMemberId(MEMBER_ID_1);
        Board board = findBoards.get(0);

        String newTitle = "NEW_TITLE";
        String newContent = "NEW_TITLE";

        Board forUpdate = Board.builder()
            .id(board.getId())
            .title(newTitle)
            .content(newContent)
            .build();

        boardDao.update(forUpdate);

        Board afterUpdate = boardDao.selectById(board.getId());
        assertThat(afterUpdate).isNotNull();

        assertThat(afterUpdate.getId()).isEqualTo(board.getId());
        assertThat(afterUpdate.getTitle()).isEqualTo(newTitle);
        assertThat(afterUpdate.getContent()).isEqualTo(newContent);
        assertThat(afterUpdate.getMemberId()).isEqualTo(board.getMemberId());
    }

    @Test
    @DisplayName("파라미터가 없는 업데이트 확인")
    void updateNull() {
        List<Board> findBoards = boardDao.findByMemberId(MEMBER_ID_1);
        Board board = findBoards.get(0);

        Board forUpdate = Board.builder()
            .id(board.getId())
            .build();

        boardDao.update(forUpdate);

        Board afterUpdate = boardDao.selectById(board.getId());
        assertThat(afterUpdate).isNotNull();

        assertThat(afterUpdate.getId()).isEqualTo(board.getId());
        assertThat(afterUpdate.getTitle()).isEqualTo(board.getTitle());
        assertThat(afterUpdate.getContent()).isEqualTo(board.getContent());
        assertThat(afterUpdate.getMemberId()).isEqualTo(board.getMemberId());
    }

    @Test
    @DisplayName("삭제 후 개수 테스트 ex) (2 -> 1)")
    void delete() {
        List<Board> boards = boardDao.findByMemberId(MEMBER_ID_1);
        Board one = boards.get(0);
        int beforeSize = boards.size();

        boardDao.delete(one);
        int afterSize = boardDao.findByMemberId(MEMBER_ID_1).size();

        assertThat(beforeSize - afterSize).isEqualTo(1);
    }

    @Test
    @DisplayName("타이틀로 검색 개수만 테스트")
    void findLikeTitle() {
        assertThat(boardDao.findLikeTitle(BOARD_COMMON_TITLE).size()).isEqualTo(2);
        assertThat(boardDao.findLikeTitle(GARBAGE).size()).isEqualTo(0);
    }

    @Test
    @DisplayName("컨텐츠로 검색 개수만 테스트")
    void findLikeContent() {
        assertThat(boardDao.findLikeContent(BOARD_COMMON_CONTENT).size()).isEqualTo(2);
        assertThat(boardDao.findLikeContent(GARBAGE).size()).isEqualTo(0);
    }

    @Test
    @DisplayName("멤버 아이디로 검색 테스트")
    void findByMemberId() {
        List<Board> findList = boardDao.findByMemberId(MEMBER_ID_1);
        assertThat(findList.size()).isEqualTo(1);
        Board board = findList.get(0);

        List<Board> likeTitle = boardDao.findLikeTitle(BOARD_TITLE_1);
        assertThat(likeTitle.size()).isEqualTo(1);
        assertThat(board).isEqualTo(likeTitle.get(0));
    }

    @Test
    @DisplayName("BeforeEach 로 등록된 게시판 수 테스트")
    void selectAll() {
        int beforeSize = boardDao.selectAll().size();

        Board board = Board.builder()
            .title(UUID.randomUUID().toString())
            .content(UUID.randomUUID().toString())
            .memberId(MEMBER_ID_1)
            .build();
        boardDao.create(board);

        int afterSize = boardDao.selectAll().size();

        assertThat(afterSize - beforeSize).isEqualTo(1);
    }
}