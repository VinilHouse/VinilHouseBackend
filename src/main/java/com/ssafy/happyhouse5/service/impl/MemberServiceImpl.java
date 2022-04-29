package com.ssafy.happyhouse5.service.impl;

import com.ssafy.happyhouse5.dao.MemberDao;
import com.ssafy.happyhouse5.dto.member.Member;
import com.ssafy.happyhouse5.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberServiceImpl implements MemberService {

    private final MemberDao memberDao;

    @Override
    public boolean checkExists(String id) {
        return memberDao.findById(id) != null;
    }

    @Override
    @Transactional
    public void register(Member member) {
        memberDao.register(member);
    }

    @Override
    public boolean login(String id, String password) {
        Member findMember = memberDao.findById(id);
        if(findMember == null) return false;
        return findMember.getPassword().equals(password);
    }

    @Override
    @Transactional
    public void update(Member member) {
        memberDao.update(member);
    }

    @Override
    @Transactional
    public void delete(Member member) {
        memberDao.delete(member);
    }

    @Override
    public Member findMemberById(String id) {
        return memberDao.findById(id);
    }

    @Override
    public Member findMemberByEmail(String email) {
        return memberDao.findByEmail(email);
    }
}
