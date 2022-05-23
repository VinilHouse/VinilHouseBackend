package com.ssafy.happyhouse5.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.ssafy.happyhouse5.dto.comment.CommentRegistDto;
import com.ssafy.happyhouse5.dto.comment.CommentUpdateDto;
import com.ssafy.happyhouse5.dto.member.MemberUpdateDto;
import com.ssafy.happyhouse5.entity.Comment;
import com.ssafy.happyhouse5.entity.Favorite;
import com.ssafy.happyhouse5.entity.HouseInfo;
import com.ssafy.happyhouse5.entity.Member;
import com.ssafy.happyhouse5.exception.comment.CommentNotFoundException;
import com.ssafy.happyhouse5.exception.favorite.FavoriteDuplicateException;
import com.ssafy.happyhouse5.exception.favorite.FavoriteNotFoundException;
import com.ssafy.happyhouse5.exception.house.HouseInfoNotFoundException;
import com.ssafy.happyhouse5.exception.member.MemberAuthException;
import com.ssafy.happyhouse5.exception.member.MemberNotFoundException;
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
class MemberServiceTest {

    public static final String MEMBER_ID = "member1";
    public static final String MEMBER_PASSWORD = "password1";
    public static final String MEMBER_EMAIL = "email1@ssafy.com";
    public static final String GARBAGE_VALUE = "GARBAGE";

    @Autowired
    MemberService memberService;

    @PersistenceContext
    EntityManager em;

    @BeforeEach
    void before() {
        Member member = Member.builder()
            .ident(MEMBER_ID)
            .password(MEMBER_PASSWORD)
            .email(MEMBER_EMAIL)
            .build();

        em.persist(member);
    }

    @Test
    @DisplayName("단순 회원 가입 테스트")
    void register() {
        Member findMember = memberService.findMemberByIdent(MEMBER_ID);
        assertThat(findMember).isNotNull();
        assertThat(findMember.getEmail()).isEqualTo(MEMBER_EMAIL);
    }

    @Test
    @DisplayName("아이디 비밀번호 일치 여부에 따른 로그인 반환 값 확인")
    void login() {
        assertThat(memberService.login(MEMBER_ID, MEMBER_PASSWORD))
            .isEqualTo(true);
        assertThrows(MemberNotFoundException.class,
            () -> memberService.login(MEMBER_ID + GARBAGE_VALUE, MEMBER_PASSWORD + GARBAGE_VALUE));
        assertThrows(MemberNotFoundException.class,
            () -> memberService.login(MEMBER_ID + GARBAGE_VALUE, MEMBER_PASSWORD));
        assertThat(memberService.login(MEMBER_ID, MEMBER_PASSWORD + GARBAGE_VALUE))
            .isEqualTo(false);
    }

    @Test
    @DisplayName("비밀번호 변경(업데이트) 테스트")
    void update() {
        String newPassword = "NewPassword";

        Member findMember = memberService.findMemberByIdent(MEMBER_ID);
        MemberUpdateDto memberUpdateDto = new MemberUpdateDto();
        memberUpdateDto.setPassword(newPassword);

        memberService.update(findMember.getId(), memberUpdateDto);
        Member afterMember = memberService.findMemberByEmail(findMember.getEmail());

        assertThat(afterMember).isNotNull();
        assertThat(afterMember.getPassword()).isEqualTo(newPassword);
        assertThat(afterMember.getIdent()).isEqualTo(findMember.getIdent());
    }

    @Test
    @DisplayName("회원 삭제 테스트")
    void delete() {
        Member findMember = memberService.findMemberByIdent(MEMBER_ID);
        assertThat(findMember).isNotNull();

        memberService.delete(findMember.getId());

        assertThrows(RuntimeException.class,
            () -> memberService.findMemberByIdent(MEMBER_ID));
    }

    @Test
    @DisplayName("단순 회원 ID로 조회 테스트")
    void findMemberById() {
        assertThat(memberService.findMemberByIdent(MEMBER_ID)).isNotNull();
        assertThrows(RuntimeException.class,
            () -> memberService.findMemberByIdent(MEMBER_ID + GARBAGE_VALUE));
    }

    @Test
    @DisplayName("단순 회원 EMAIL 로 조회 테스트")
    void findMemberByEmail() {
        assertThat(memberService.findMemberByEmail(MEMBER_EMAIL)).isNotNull();
        assertThrows(RuntimeException.class,
            () -> memberService.findMemberByEmail(MEMBER_EMAIL + GARBAGE_VALUE));
    }

    @Test
    @DisplayName("즐겨찾기 등록 테스트")
    void createFavoriteTest() {
        Member member = memberService.findMemberByIdent(MEMBER_ID);
        HouseInfo houseInfo = new HouseInfo("apt1");

        em.persist(houseInfo);

        Long favoriteId = memberService.enableFavorite(member.getId(), houseInfo.getAptCode());
        Favorite favorite = em.find(Favorite.class, favoriteId);
        assertThat(favorite).isNotNull();
        assertThat(favorite.getMember()).isEqualTo(member);
        assertThat(favorite.getHouseInfo()).isEqualTo(houseInfo);
    }

    @Test
    @DisplayName("즐겨찾기 등록 해제 테스트")
    void disableFavoriteTest() {
        Member member = memberService.findMemberByIdent(MEMBER_ID);
        HouseInfo houseInfo = new HouseInfo("apt1");

        em.persist(member);
        em.persist(houseInfo);

        Long favoriteId = memberService.enableFavorite(member.getId(), houseInfo.getAptCode());
        Long disabledId = memberService.disableFavorite(member.getId(), houseInfo.getAptCode());

        assertThat(favoriteId).isNotNull();
        assertThat(disabledId).isNotNull();
        assertThat(favoriteId).isEqualTo(disabledId);
        assertThat(em.createQuery("select f from Favorite f").getResultList().size())
            .isEqualTo(0);
    }

    @Test
    @DisplayName("없는 아파트 등록 즐겨찾기 예외 테스트")
    void notExistHouseInfoFavoriteTest() {
        Member member = memberService.findMemberByIdent(MEMBER_ID);
        em.persist(member);

        assertThrows(HouseInfoNotFoundException.class,
            () -> memberService.enableFavorite(member.getId(), 999L));
    }

    @Test
    @DisplayName("없는 멤버가 즐겨찾기 등록하는 예외 테스트")
    void notExistMemberFavoriteTest() {
        HouseInfo houseInfo = new HouseInfo("apt1");
        em.persist(houseInfo);

        assertThrows(MemberNotFoundException.class,
            () -> memberService.enableFavorite(999L, houseInfo.getAptCode()));
    }

    @Test
    @DisplayName("존재하지 않는 즐겨찾기 해제하는 경우 예외 테스트")
    void notExistFavoriteTest() {
        Member member = memberService.findMemberByIdent(MEMBER_ID);
        HouseInfo houseInfo = new HouseInfo("apt1");

        em.persist(member);
        em.persist(houseInfo);

        assertThrows(FavoriteNotFoundException.class,
            () -> memberService.disableFavorite(member.getId(), houseInfo.getAptCode()));
    }

    @Test
    @DisplayName("즐겨찾기 중복 등록 테스트")
    void duplicateFavoriteTest() {
        Member member = memberService.findMemberByIdent(MEMBER_ID);
        HouseInfo houseInfo = new HouseInfo("apt1");

        em.persist(member);
        em.persist(houseInfo);

        memberService.enableFavorite(member.getId(), houseInfo.getAptCode());
        assertThrows(FavoriteDuplicateException.class,
            () -> memberService.enableFavorite(member.getId(), houseInfo.getAptCode()));
    }

    @Test
    @DisplayName("즐겨찾기로 등록된 HouseInfo 조회 테스트")
    void findHouseInfoByMember() {
        Member member = memberService.findMemberByIdent(MEMBER_ID);
        HouseInfo houseInfo1 = new HouseInfo("apt1");
        HouseInfo houseInfo2 = new HouseInfo("apt2");

        em.persist(member);
        em.persist(houseInfo1);
        em.persist(houseInfo2);

        memberService.enableFavorite(member.getId(), houseInfo1.getAptCode());
        memberService.enableFavorite(member.getId(), houseInfo2.getAptCode());

        List<HouseInfo> favoriteHouseInfos = memberService.getFavoriteHouseInfo(member.getId());
        assertThat(favoriteHouseInfos).contains(houseInfo1, houseInfo2);
    }

    @Test
    @DisplayName("아파트 댓글 등록 테스트")
    void createCommentTest() {
        Member member = memberService.findMemberByIdent(MEMBER_ID);
        HouseInfo houseInfo = new HouseInfo("apt1");

        em.persist(member);
        em.persist(houseInfo);

        CommentRegistDto commentRegistDto = new CommentRegistDto();
        commentRegistDto.setTitle("comment title");
        commentRegistDto.setContent("comment content");

        Long commentId = memberService.createComment(member.getId(), houseInfo.getAptCode()
            , commentRegistDto);

        Comment findComment = em.find(Comment.class, commentId);

        assertThat(findComment.getTitle()).isEqualTo(commentRegistDto.getTitle());
        assertThat(findComment.getContent()).isEqualTo(commentRegistDto.getContent());
    }

    @Test
    @DisplayName("존재하지 않는 회원의 아파트 댓글 등록 테스트")
    void createCommentTestNotExistMember() {
        HouseInfo houseInfo = new HouseInfo("apt1");

        em.persist(houseInfo);

        CommentRegistDto commentRegistDto = new CommentRegistDto();
        commentRegistDto.setTitle("comment title");
        commentRegistDto.setContent("comment content");

        assertThrows(MemberNotFoundException.class, () ->
            memberService.createComment(999L, houseInfo.getAptCode(), commentRegistDto));
    }

    @Test
    @DisplayName("존재하지 않는 아파트에 대한 댓글 등록 테스트")
    void createCommentTestNotExistApt() {
        Member member = memberService.findMemberByIdent(MEMBER_ID);
        HouseInfo houseInfo = new HouseInfo("apt1");

        em.persist(member);
        em.persist(houseInfo);

        CommentRegistDto commentRegistDto = new CommentRegistDto();
        commentRegistDto.setTitle("new comment title");
        commentRegistDto.setContent("new comment content");

        assertThrows(HouseInfoNotFoundException.class,
            () -> memberService.createComment(member.getId(), 9876543210L, commentRegistDto));
    }

    @Test
    @DisplayName("존재하지 않는 아파트 댓글에 대한 수정/삭제 테스트")
    void updateCommentNotExistCommentCaseTest() {
        Member member = memberService.findMemberByIdent(MEMBER_ID);

        em.persist(member);

        CommentUpdateDto commentUpdateDto = new CommentUpdateDto();
        commentUpdateDto.setTitle("comment title");
        commentUpdateDto.setContent("comment content");

        assertThrows(CommentNotFoundException.class,
            () -> memberService.updateComment(member.getId(), 9876543210L, commentUpdateDto));

        assertThrows(CommentNotFoundException.class,
            () -> memberService.deleteComment(member.getId(), 9876543210L));
    }

    @Test
    @DisplayName("정상적인 아파트 댓글을 수정하는 테스트")
    void updateCommentTest() {
        Member member = memberService.findMemberByIdent(MEMBER_ID);
        HouseInfo houseInfo = new HouseInfo("apt1");

        Comment comment = new Comment();
        comment.setMember(member);
        comment.setHouseInfo(houseInfo);

        em.persist(member);
        em.persist(houseInfo);
        em.persist(comment);

        CommentUpdateDto commentUpdateDto = new CommentUpdateDto();
        commentUpdateDto.setTitle("new Title");
        commentUpdateDto.setContent("new Content");

        memberService.updateComment(member.getId(), comment.getId(), commentUpdateDto);

        Comment updatedComment = em.find(Comment.class, comment.getId());

        assertThat(updatedComment).isNotNull();

        assertThat(updatedComment.getTitle()).isEqualTo(commentUpdateDto.getTitle());
        assertThat(updatedComment.getContent()).isEqualTo(commentUpdateDto.getContent());
    }
}