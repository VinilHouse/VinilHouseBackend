package com.ssafy.happyhouse5.service.impl;

import com.ssafy.happyhouse5.dto.member.MemberRegisterDto;
import com.ssafy.happyhouse5.dto.member.MemberUpdateDto;
import com.ssafy.happyhouse5.entity.Favorite;
import com.ssafy.happyhouse5.entity.HouseInfo;
import com.ssafy.happyhouse5.entity.Member;
import com.ssafy.happyhouse5.exception.favorite.FavoriteDuplicateException;
import com.ssafy.happyhouse5.exception.favorite.FavoriteNotFoundException;
import com.ssafy.happyhouse5.exception.house.HouseInfoNotFoundException;
import com.ssafy.happyhouse5.exception.member.MemberNotFoundException;
import com.ssafy.happyhouse5.repository.FavoriteRepository;
import com.ssafy.happyhouse5.repository.HouseInfoRepository;
import com.ssafy.happyhouse5.repository.MemberRepository;
import com.ssafy.happyhouse5.service.MemberService;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberServiceImpl implements MemberService {

    private final MemberRepository memberRepository;

    private final FavoriteRepository favoriteRepository;

    private final HouseInfoRepository houseInfoRepository;

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
        return checkExistAndGetMember(memberRepository.findMemberByIdent(ident)
        ).getPassword().equals(password);
    }

    @Override
    @Transactional
    public void update(Long id, MemberUpdateDto memberUpdateDto) {
        Member member = checkExistAndGetMember(memberRepository.findById(id));
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
        return checkExistAndGetMember(memberRepository.findById(id));
    }

    @Override
    public Member findMemberByIdent(String ident) {
        return checkExistAndGetMember(memberRepository.findMemberByIdent(ident));
    }

    @Override
    public Member findMemberByEmail(String email) {
        return checkExistAndGetMember(memberRepository.findMemberByEmail(email));
    }

    @Override
    @Transactional
    public Long enableFavorite(Long memberId, Long aptCode) {
        Member member = checkExistAndGetMember(memberRepository.findById(memberId));
        HouseInfo houseInfo = checkExistAndGetHouseInfoByAptCode(aptCode);
        if(favoriteRepository.findByMemberAndHouseInfo(member, houseInfo)
            .isPresent()){
            throw new FavoriteDuplicateException();
        }

        Favorite favorite = new Favorite();
        favorite.setMember(member);
        favorite.setHouseInfo(houseInfo);
        return favoriteRepository.save(favorite).getId();
    }

    @Override
    @Transactional
    public Long disableFavorite(Long memberId, Long aptCode) {
        Member member = checkExistAndGetMember(memberRepository.findById(memberId));
        HouseInfo houseInfo = checkExistAndGetHouseInfoByAptCode(aptCode);
        Favorite favorite = favoriteRepository.findByMemberAndHouseInfo(member, houseInfo)
            .orElseThrow(FavoriteNotFoundException::new);
        favoriteRepository.delete(favorite);
        return favorite.getId();
    }

    @Override
    public List<HouseInfo> getFavoriteHouseInfo(Long memberId) {
        Member member = checkExistAndGetMember(memberRepository.findById(memberId));
        return favoriteRepository.findFavoriteByMemberWithHouseInfo(member).stream()
            .map(Favorite::getHouseInfo)
            .collect(Collectors.toList());
    }

    private Member checkExistAndGetMember(Optional<Member> optional) {
        return optional.orElseThrow(MemberNotFoundException::new);
    }

    private HouseInfo checkExistAndGetHouseInfoByAptCode(Long aptCode) {
        return houseInfoRepository.findById(aptCode)
            .orElseThrow(HouseInfoNotFoundException::new);
    }
}
