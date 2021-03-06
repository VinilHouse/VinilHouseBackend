package com.ssafy.happyhouse5.service;


import com.ssafy.happyhouse5.dto.comment.CommentRegistDto;
import com.ssafy.happyhouse5.dto.comment.CommentUpdateDto;
import com.ssafy.happyhouse5.dto.member.dist.DistDto;
import com.ssafy.happyhouse5.dto.member.location.MemberLocationRegistDto;
import com.ssafy.happyhouse5.dto.member.location.MemberLocationUpdateDto;
import com.ssafy.happyhouse5.dto.member.MemberRegisterDto;
import com.ssafy.happyhouse5.dto.member.MemberUpdateDto;
import com.ssafy.happyhouse5.entity.HouseInfo;
import com.ssafy.happyhouse5.entity.Member;
import com.ssafy.happyhouse5.entity.MemberLocation;
import java.util.List;

public interface MemberService {

    Long register(MemberRegisterDto memberRegisterDto);

    boolean login(String id, String password);

    void update(Long id, MemberUpdateDto memberUpdateDto);

    void delete(Long id);

    Member findMemberById(Long id);

    Member findMemberByIdent(String ident);

    Member findMemberByEmail(String email);

    Long enableFavorite(Long memberId, Long aptCode);

    Long disableFavorite(Long memberId, Long aptCode);

    List<HouseInfo> getFavoriteHouseInfo(Long memberId);

    Long createComment(Long memberId, CommentRegistDto commentRegistDto);

    Long updateComment(Long memberId, CommentUpdateDto commentUpdateDto);

    Long deleteComment(Long memberId, Long commentId);

    Long createMemberLocation(Long memberId, MemberLocationRegistDto registDto);

    Long updateMemberLocation(Long memberId, MemberLocationUpdateDto updateDto);

    Long deleteMemberLocation(Long memberId, Long locationId);

    List<MemberLocation> getMemberLocation(Long memberId);

    DistDto getDistList(Long memberId, Long aptCode);
}
