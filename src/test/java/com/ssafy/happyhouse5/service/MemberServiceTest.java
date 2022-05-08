package com.ssafy.happyhouse5.service;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import com.ssafy.happyhouse5.dto.member.MemberUpdateDto;
import com.ssafy.happyhouse5.entity.Member;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.junit.jupiter.api.Assertions;
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
}