package com.ssafy.happyhouse5.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.ssafy.happyhouse5.dto.member.MemberUpdateDto;
import com.ssafy.happyhouse5.entity.Favorite;
import com.ssafy.happyhouse5.entity.HouseInfo;
import com.ssafy.happyhouse5.entity.Member;
import com.ssafy.happyhouse5.exception.favorite.FavoriteNotFoundException;
import com.ssafy.happyhouse5.exception.house.HouseInfoNotFoundException;
import com.ssafy.happyhouse5.exception.member.MemberNotFoundException;
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
//        assertThat(memberService.login(MEMBER_ID + GARBAGE_VALUE, MEMBER_PASSWORD + GARBAGE_VALUE))
//            .isEqualTo(false);
//        assertThat(memberService.login(MEMBER_ID, MEMBER_PASSWORD + GARBAGE_VALUE))
//            .isEqualTo(false);
//        assertThat(memberService.login(MEMBER_ID + GARBAGE_VALUE, MEMBER_PASSWORD))
//            .isEqualTo(false);
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
        Member member = new Member("member1");
        HouseInfo houseInfo = new HouseInfo("apt1");

        em.persist(member);
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
        Member member = new Member("member1");
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
        Member member = new Member("member1");
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
        Member member = new Member("member1");
        HouseInfo houseInfo = new HouseInfo("apt1");

        em.persist(member);
        em.persist(houseInfo);

        assertThrows(FavoriteNotFoundException.class,
            () -> memberService.disableFavorite(member.getId(), houseInfo.getAptCode()));
    }
}