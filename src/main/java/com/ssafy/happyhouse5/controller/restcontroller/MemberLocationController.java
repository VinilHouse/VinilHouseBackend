package com.ssafy.happyhouse5.controller.restcontroller;

import static com.ssafy.happyhouse5.constant.MemberConst.MEMBER_SESSION;

import com.ssafy.happyhouse5.dto.common.Response;
import com.ssafy.happyhouse5.dto.member.location.MemberLocationRegistDto;
import com.ssafy.happyhouse5.dto.member.location.MemberLocationResponse;
import com.ssafy.happyhouse5.dto.member.location.MemberLocationUpdateDto;
import com.ssafy.happyhouse5.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.SessionAttribute;

@RequestMapping("/api/members/location")
@RestController
@RequiredArgsConstructor
public class MemberLocationController {

    private final MemberService memberService;

    @GetMapping
    public ResponseEntity<Response> getMemberLocation(
        @SessionAttribute(MEMBER_SESSION) Long memberId) {
        return ResponseEntity.ok(Response.success(
            memberService.getMemberLocation(memberId).stream()
                .map(MemberLocationResponse::new)));
    }

    @PostMapping
    public ResponseEntity<Response> postMemberLocation(
        @SessionAttribute(MEMBER_SESSION) Long memberId,
        @RequestBody MemberLocationRegistDto registDto) {
        Long locationId = memberService.createMemberLocation(memberId, registDto);
        return ResponseEntity.ok(Response.success(locationId));
    }

    @PutMapping
    public ResponseEntity<Response> updateMemberLocation(
        @SessionAttribute(MEMBER_SESSION) Long memberId,
        @RequestBody MemberLocationUpdateDto updateDto
    ) {
        Long locationId = memberService.updateMemberLocation(memberId, updateDto);
        return ResponseEntity.ok(Response.success(locationId));
    }

    @DeleteMapping
    public ResponseEntity<Response> deleteMemberLocation(
        @SessionAttribute(MEMBER_SESSION) Long memberId,
        @RequestBody Long locationId) {
        memberService.deleteMemberLocation(memberId, locationId);
        return ResponseEntity.ok(Response.success(locationId));
    }
}
