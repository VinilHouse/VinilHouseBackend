package com.ssafy.happyhouse5.service.impl;

import com.ssafy.happyhouse5.dto.comment.CommentRegistDto;
import com.ssafy.happyhouse5.dto.comment.CommentUpdateDto;
import com.ssafy.happyhouse5.dto.member.location.MemberLocationRegistDto;
import com.ssafy.happyhouse5.dto.member.location.MemberLocationUpdateDto;
import com.ssafy.happyhouse5.dto.member.MemberRegisterDto;
import com.ssafy.happyhouse5.dto.member.MemberUpdateDto;
import com.ssafy.happyhouse5.entity.Comment;
import com.ssafy.happyhouse5.entity.Favorite;
import com.ssafy.happyhouse5.entity.HouseInfo;
import com.ssafy.happyhouse5.entity.Member;
import com.ssafy.happyhouse5.entity.MemberLocation;
import com.ssafy.happyhouse5.exception.comment.CommentNotFoundException;
import com.ssafy.happyhouse5.exception.favorite.FavoriteDuplicateException;
import com.ssafy.happyhouse5.exception.favorite.FavoriteNotFoundException;
import com.ssafy.happyhouse5.exception.house.HouseInfoNotFoundException;
import com.ssafy.happyhouse5.exception.member.MemberAuthException;
import com.ssafy.happyhouse5.exception.member.MemberDuplicateIdentException;
import com.ssafy.happyhouse5.exception.member.MemberLocationNotFoundException;
import com.ssafy.happyhouse5.exception.member.MemberNotFoundException;
import com.ssafy.happyhouse5.repository.CommentRepository;
import com.ssafy.happyhouse5.repository.FavoriteRepository;
import com.ssafy.happyhouse5.repository.HouseInfoRepository;
import com.ssafy.happyhouse5.repository.MemberLocationRepository;
import com.ssafy.happyhouse5.repository.MemberRepository;
import com.ssafy.happyhouse5.service.MemberService;
import com.ssafy.happyhouse5.util.AddressConverter;
import com.ssafy.happyhouse5.util.LatLng;
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

    private final CommentRepository commentRepository;

    private final MemberLocationRepository memberLocationRepository;

    private final AddressConverter addressConverter;

    @Override
    @Transactional
    public Long register(MemberRegisterDto memberRegisterDto) {
        Member member = Member.builder()
            .ident(memberRegisterDto.getIdent())
            .password(memberRegisterDto.getPassword())
            .email(memberRegisterDto.getEmail())
            .build();
        checkAlreadyExistMemberIdent(member.getIdent());
        memberRepository.save(member);
        return member.getId();
    }

    @Override
    public boolean login(String ident, String password) {
        return checkExistAndGetMember(memberRepository.findMemberByIdent(ident))
            .getPassword().equals(password);
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
        if (favoriteRepository.findByMemberAndHouseInfo(member, houseInfo)
            .isPresent()) {
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

    @Override
    @Transactional
    public Long createComment(Long memberId, CommentRegistDto commentRegistDto) {
        Long aptCode = commentRegistDto.getAptCode();
        Member member = checkExistAndGetMember(memberRepository.findById(memberId));
        HouseInfo houseInfo = checkExistAndGetHouseInfoByAptCode(aptCode);

        Comment comment = new Comment();
        comment.setMember(member);
        comment.setHouseInfo(houseInfo);
        comment.setTitle(commentRegistDto.getTitle());
        comment.setContent(commentRegistDto.getContent());

        commentRepository.save(comment);
        return comment.getId();
    }

    @Override
    @Transactional
    public Long updateComment(Long memberId, CommentUpdateDto commentUpdateDto) {
        Long commentId = commentUpdateDto.getCommentId();

        Comment comment = checkExistAndGetCommentByCommentId(commentId);
        if (!comment.getMember().getId().equals(memberId)) {
            throw new MemberAuthException();
        }

        comment.setTitle(commentUpdateDto.getTitle());
        comment.setContent(commentUpdateDto.getContent());
        return commentId;
    }

    @Override
    @Transactional
    public Long deleteComment(Long memberId, Long commentId) {
        Comment comment = checkExistAndGetCommentByCommentId(commentId);
        if (!comment.getMember().getId().equals(memberId)) {
            throw new MemberAuthException();
        }
        commentRepository.delete(comment);
        return commentId;
    }

    @Override
    @Transactional
    public Long createMemberLocation(Long memberId, MemberLocationRegistDto registDto) {
        Member member = checkExistAndGetMember(memberRepository.findById(memberId));

        MemberLocation memberLocation = new MemberLocation();
        memberLocation.setMember(member);
        memberLocation.setAddress(registDto.getAddress());
        memberLocation.setAlias(registDto.getAlias());

        LatLng latLng = addressConverter.convert(registDto.getAddress());
        memberLocation.setLat(latLng.getLat());
        memberLocation.setLng(latLng.getLng());

        memberLocationRepository.save(memberLocation);
        return memberLocation.getId();
    }

    @Override
    @Transactional
    public Long updateMemberLocation(Long memberId, MemberLocationUpdateDto updateDto) {
        checkExistAndGetMember(memberRepository.findById(memberId));
        MemberLocation memberLocation
            = checkExistAndGetMemberLocationByLocationId(updateDto.getId());

        if (!memberLocation.getMember().getId().equals(memberId)) {
            throw new MemberAuthException();
        }

        memberLocation.setAddress(updateDto.getAddress());
        memberLocation.setAlias(updateDto.getAlias());

        LatLng latLng = addressConverter.convert(updateDto.getAddress());
        memberLocation.setLat(latLng.getLat());
        memberLocation.setLng(latLng.getLng());

        return memberLocation.getId();
    }

    @Override
    @Transactional
    public Long deleteMemberLocation(Long memberId, Long locationId) {
        MemberLocation memberLocation
            = checkExistAndGetMemberLocationByLocationId(locationId);
        if (!memberLocation.getMember().getId().equals(memberId)) {
            throw new MemberAuthException();
        }
        memberLocationRepository.delete(memberLocation);
        return memberLocation.getId();
    }

    @Override
    public List<MemberLocation> getMemberLocation(Long memberId) {
        checkExistAndGetMember(memberRepository.findById(memberId));
        return memberLocationRepository.findByMemberId(memberId);
    }

    private Member checkExistAndGetMember(Optional<Member> optional) {
        return optional.orElseThrow(MemberNotFoundException::new);
    }

    private HouseInfo checkExistAndGetHouseInfoByAptCode(Long aptCode) {
        return houseInfoRepository.findById(aptCode)
            .orElseThrow(HouseInfoNotFoundException::new);
    }

    private Comment checkExistAndGetCommentByCommentId(Long commentId) {
        return commentRepository.findById(commentId)
            .orElseThrow(CommentNotFoundException::new);
    }

    private void checkAlreadyExistMemberIdent(String ident) {
        if (memberRepository.findMemberByIdent(ident).isPresent()) {
            throw new MemberDuplicateIdentException();
        }
    }

    private MemberLocation checkExistAndGetMemberLocationByLocationId(Long locationId) {
        return memberLocationRepository.findById(locationId)
            .orElseThrow(MemberLocationNotFoundException::new);
    }
}
