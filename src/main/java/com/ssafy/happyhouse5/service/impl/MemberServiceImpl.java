package com.ssafy.happyhouse5.service.impl;

import static com.ssafy.happyhouse5.constant.MemberConst.MEMBER_LOGIN_FAIL_MSG;
import static com.ssafy.happyhouse5.constant.MemberConst.MEMBER_NOT_FOUND;

import com.ssafy.happyhouse5.dto.member.MemberRegisterDto;
import com.ssafy.happyhouse5.dto.member.MemberUpdateDto;
import com.ssafy.happyhouse5.entity.Member;
import com.ssafy.happyhouse5.repository.MemberRepository;
import com.ssafy.happyhouse5.service.MemberService;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberServiceImpl implements MemberService {

    private final MemberRepository memberRepository;

    @Override
    @Transactional
    public Long register(MemberRegisterDto memberRegisterDto) {
        Member member = Member.builder()
            .ident(memberRegisterDto.getIdent())
            .password(memberRegisterDto.getPassword())
            .email(memberRegisterDto.getEmail())
            .build();
        memberRepository.save(member);
        return member.getId();
    }

    @Override
    public boolean login(String ident, String password) {
        return checkExistAndGetMember(memberRepository.findMemberByIdent(ident),
            MEMBER_LOGIN_FAIL_MSG).getPassword().equals(password);
    }

    @Override
    @Transactional
    public void update(Long id, MemberUpdateDto memberUpdateDto) {
        Member member = checkExistAndGetMember(memberRepository.findById(id), MEMBER_NOT_FOUND);
        member.setPassword(memberUpdateDto.getPassword());
        member.setEmail(memberUpdateDto.getEmail());
    }

    @Override
    @Transactional
    public void delete(Long id) {
        memberRepository.deleteById(id);
    }

    @Override
    public Member findMemberById(Long id) {
        return checkExistAndGetMember(memberRepository.findById(id), MEMBER_NOT_FOUND);
    }

    @Override
    public Member findMemberByIdent(String ident) {
        return checkExistAndGetMember(memberRepository.findMemberByIdent(ident), MEMBER_NOT_FOUND);
    }

    @Override
    public Member findMemberByEmail(String email) {
        return checkExistAndGetMember(memberRepository.findMemberByEmail(email), MEMBER_NOT_FOUND);
    }

    private Member checkExistAndGetMember(Optional<Member> memberRepository, String msg) {
        return memberRepository.orElseThrow(
            () -> new IllegalArgumentException(msg));
    }
}
