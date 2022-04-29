package com.ssafy.happyhouse5.dao;

import com.ssafy.happyhouse5.dto.member.Member;

public interface MemberDao {

    void register(Member member);

    void update(Member member);

    Member findById(String id);

    Member findByEmail(String email);

    void delete(Member member);
}
