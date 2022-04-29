package com.ssafy.happyhouse5.service;

import com.ssafy.happyhouse5.dto.member.Member;

public interface MemberService {

    boolean checkExists(String id);

    void register(Member member);

    boolean login(String id, String pass);

    void update(Member member);

    void delete(Member member);

    Member findMemberById(String id);

    Member findMemberByEmail(String email);
}
