package com.ssafy.happyhouse5.dao;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.ssafy.happyhouse5.dto.member.Member;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
class MemberDaoTest {

    @Autowired
    MemberDao memberDao;

    @Test
    @DisplayName("사용자 회원가입 및 사용자 Id로 조회 테스트")
    void register() {
        Member member = Member.builder()
            .id("member1")
            .password("password1")
            .email("email1@ssafy.com")
            .build();

        memberDao.register(member);

        Member findMember = memberDao.findById("member1");
        assertThat(findMember).isEqualTo(member);
    }

    @Test
    @DisplayName("Null 사용자 Id 조회 테스트")
    void registerNull() {
        Member member = Member.builder().build();

        assertThrows(DataIntegrityViolationException.class, () -> memberDao.register(member));
    }

    @Test
    @DisplayName("존재하지 않는 사용자 Id 조회 테스트")
    void registerNotExists() {
        Member findMember = memberDao.findById("member12323xxx");
        assertThat(findMember).isNull();
    }

    @Test
    void update() {
        Member member = Member.builder()
            .id("member1")
            .password("password1")
            .email("email1@ssafy.com")
            .build();

        memberDao.register(member);
        member.setPassword("newPassword");
        memberDao.update(member);

        Member findMember = memberDao.findById("member1");
        assertThat(findMember).isEqualTo(member);
    }

    @Test
    @DisplayName("회원 email로 조회 Test")
    void findByEmail() {
        Member member = Member.builder()
            .id("member1")
            .password("password1")
            .email("email1@ssafy.com")
            .build();

        memberDao.register(member);
        Member findMember = memberDao.findByEmail("email1@ssafy.com");

        assertThat(findMember).isNotNull();
        assertThat(findMember).isEqualTo(member);
    }

    @Test
    @DisplayName("기본 회원 삭제 테스트")
    void delete() {
        Member member = Member.builder()
            .id("member1")
            .password("password1")
            .email("email1@ssafy.com")
            .build();

        memberDao.register(member);
        assertThat(memberDao.findById("member1")).isNotNull();

        memberDao.delete(member);
        assertThat(memberDao.findById("member1")).isNull();
    }
}