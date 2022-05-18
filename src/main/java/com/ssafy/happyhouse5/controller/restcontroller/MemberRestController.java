package com.ssafy.happyhouse5.controller.restcontroller;

import static com.ssafy.happyhouse5.constant.MemberConst.*;
import static com.ssafy.happyhouse5.dto.common.Response.*;
import static org.springframework.http.HttpStatus.*;

import com.ssafy.happyhouse5.dto.common.Response;
import com.ssafy.happyhouse5.dto.member.MemberLoginDto;
import com.ssafy.happyhouse5.dto.member.MemberRegisterDto;
import com.ssafy.happyhouse5.dto.member.MemberResponseDto;
import com.ssafy.happyhouse5.dto.member.MemberUpdateDto;
import com.ssafy.happyhouse5.entity.Member;
import com.ssafy.happyhouse5.exception.common.BadParamException;
import com.ssafy.happyhouse5.exception.member.MemberAuthException;
import com.ssafy.happyhouse5.exception.member.MemberNotFoundException;
import com.ssafy.happyhouse5.service.MemberService;
import java.net.URI;
import javax.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.SessionAttribute;

@Slf4j
@RequestMapping("/api/members")
@RestController
@RequiredArgsConstructor
public class MemberRestController {

    private final MemberService memberService;

    @PostMapping
    public ResponseEntity<Response> register(
        @Validated @RequestBody MemberRegisterDto memberRegisterDto,
        BindingResult bindingResult) {

        checkHasBindingError(bindingResult);

        memberService.register(memberRegisterDto);

        return ResponseEntity.created(URI.create("/api/members/" + memberRegisterDto.getIdent()))
            .body(success(memberRegisterDto.getIdent()));
    }

    @PostMapping("/login")
    public ResponseEntity<Response> login(
        @RequestBody @Validated MemberLoginDto memberLoginDto,
        BindingResult bindingResult,
        HttpSession session) {

        checkHasBindingError(bindingResult);

        if (!memberService.login(memberLoginDto.getIdent(), memberLoginDto.getPassword())) {
            throw new MemberAuthException();
        }

        Member member = memberService.findMemberByIdent(memberLoginDto.getIdent());
        session.setAttribute(MEMBER_SESSION, member.getId());
        return ResponseEntity.ok().build();
    }

    @PostMapping("/logout")
    public ResponseEntity<Response> logout(HttpSession session) {
        if (session.isNew() || session.getAttribute(MEMBER_SESSION) == null) {
            throw new MemberAuthException();
        }
        session.invalidate();
        return ResponseEntity.ok().body(success());
    }

    @PutMapping
    public ResponseEntity<Response> update(
        @SessionAttribute(MEMBER_SESSION) Long memberId,
        @RequestBody MemberUpdateDto memberUpdateDto) {
        memberService.update(memberId, memberUpdateDto);
        return ResponseEntity.accepted().build();
    }

    @DeleteMapping
    public ResponseEntity<Response> delete(HttpSession httpSession) {
        Long memberId = (Long) httpSession.getAttribute(MEMBER_SESSION);
        memberService.delete(memberId);
        httpSession.invalidate();
        return ResponseEntity.ok().build();
    }

    @GetMapping
    public ResponseEntity<Response> getMemberInfoBySession(
        @SessionAttribute(MEMBER_SESSION) Long memberId) {
        return ResponseEntity.ok().body(
            success(new MemberResponseDto(memberService.findMemberById(memberId))));
    }

    @GetMapping("/id")
    public ResponseEntity<Response> findByIdent(@RequestParam(required = false) String ident) {
        Member findMember = memberService.findMemberByIdent(ident);
        if (findMember == null) {
            throw new MemberNotFoundException();
        }
        return ResponseEntity.ok(
            success(new ResponseEntity<>(new MemberResponseDto(findMember), OK)));
    }

    @GetMapping("/email")
    public ResponseEntity<Response> findByEmail(@RequestParam(required = false) String email) {
        Member findMember = memberService.findMemberByEmail(email);
        if (findMember == null) {
            throw new MemberNotFoundException();
        }
        return ResponseEntity.ok(success(new MemberResponseDto(findMember)));
    }

    private void checkHasBindingError(BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new BadParamException();
        }
    }
}
