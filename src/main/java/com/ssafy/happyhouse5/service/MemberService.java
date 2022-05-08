package com.ssafy.happyhouse5.service;


import com.ssafy.happyhouse5.dto.member.MemberRegisterDto;
import com.ssafy.happyhouse5.dto.member.MemberUpdateDto;
import com.ssafy.happyhouse5.entity.Member;

public interface MemberService {

    Long register(MemberRegisterDto memberRegisterDto);

    boolean login(String id, String password);

    void update(Long id, MemberUpdateDto memberUpdateDto);

    void delete(Long id);

    Member findMemberById(Long id);

    Member findMemberByIdent(String ident);

    Member findMemberByEmail(String email);
}
