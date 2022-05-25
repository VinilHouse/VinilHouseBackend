package com.ssafy.happyhouse5.controller.restcontroller;

import com.ssafy.happyhouse5.constant.MemberConst;
import com.ssafy.happyhouse5.dto.common.Response;
import com.ssafy.happyhouse5.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.SessionAttribute;

@RequestMapping("/api/members/dist")
@RestController
@RequiredArgsConstructor
public class MemberDistController {

    private final MemberService memberService;

    @GetMapping
    public ResponseEntity<Response> getDistInfoFromApt(
        @SessionAttribute(MemberConst.MEMBER_SESSION) Long memberId,
        Long aptCode) {
        return ResponseEntity.ok(Response.success(memberService.getDistList(memberId, aptCode)));
    }
}
