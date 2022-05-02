package com.ssafy.happyhouse5.controller.restcontroller;

import static com.ssafy.happyhouse5.constant.MemberConst.*;
import static org.springframework.http.HttpStatus.*;

import com.ssafy.happyhouse5.dto.member.Member;
import com.ssafy.happyhouse5.dto.member.MemberLoginDto;
import com.ssafy.happyhouse5.dto.member.MemberRegisterDto;
import com.ssafy.happyhouse5.dto.member.MemberSession;
import com.ssafy.happyhouse5.dto.member.MemberUpdateDto;
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
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequestMapping("/api/members")
@RestController
@RequiredArgsConstructor
public class MemberRestController {

    private final MemberService memberService;

    @PostMapping
    public ResponseEntity<?> register(
        @Validated @RequestBody MemberRegisterDto memberRegisterDto,
        BindingResult bindingResult) {

        checkHasBindingError(bindingResult);

        memberService.register(Member.builder()
            .id(memberRegisterDto.getId())
            .password(memberRegisterDto.getPassword())
            .email(memberRegisterDto.getEmail())
            .build());

        return ResponseEntity.created(URI.create("/api/members/" + memberRegisterDto.getId()))
            .build();
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(
        @RequestBody @Validated MemberLoginDto memberLoginDto,
        BindingResult bindingResult,
        HttpSession session) {

        checkHasBindingError(bindingResult);

        if (!memberService.login(memberLoginDto.getId(), memberLoginDto.getPassword())) {
            return ResponseEntity.status(UNAUTHORIZED).body(MEMBER_LOGIN_FAIL_MSG);
        }

        Member member = memberService.findMemberById(memberLoginDto.getId());
        session.setAttribute(MEMBER_SESSION,
            MemberSession.builder()
                .id(member.getId())
                .email(member.getEmail())
                .build());
        return ResponseEntity.ok().build();
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout(HttpSession session) {
        if (session.isNew() || session.getAttribute(MEMBER_SESSION) == null) {
            return ResponseEntity.status(UNAUTHORIZED).body(MEMBER_LOGOUT_FAIL_MSG);
        }
        session.invalidate();
        return ResponseEntity.ok().build();
    }

    @PatchMapping
    public ResponseEntity<?> update(
        HttpSession httpSession,
        @RequestBody(required = false) MemberUpdateDto memberUpdateDto) {

        MemberSession member = (MemberSession) httpSession.getAttribute(MEMBER_SESSION);
        if (httpSession.isNew() || member == null) {
            return ResponseEntity.status(UNAUTHORIZED).body(MEMBER_REQUIRED_LOGIN);
        }

        if (memberUpdateDto == null) {
            return ResponseEntity.ok().build();
        }

        memberService.update(
            Member.builder()
                .id(member.getId())
                .password(memberUpdateDto.getPassword())
                .email(memberUpdateDto.getEmail())
                .build());
        return ResponseEntity.accepted().build();
    }

    @DeleteMapping
    public ResponseEntity<?> delete(HttpSession httpSession) {
        MemberSession member = (MemberSession) httpSession.getAttribute(MEMBER_SESSION);
        if (httpSession.isNew() || member == null) {
            return ResponseEntity.status(UNAUTHORIZED).body(MEMBER_REQUIRED_LOGIN);
        }
        memberService.delete(Member.builder().id(member.getId()).build());
        return ResponseEntity.ok().build();
    }

    @GetMapping("/id")
    public ResponseEntity<Member> findById(@RequestParam(required = false) String id) {
        Member findMember = memberService.findMemberById(id);
        if (findMember == null) {
            return ResponseEntity.notFound().build();
        }
        return new ResponseEntity<>(findMember, OK);
    }

    @GetMapping("/email")
    public ResponseEntity<Member> findByEmail(@RequestParam(required = false) String email) {
        Member findMember = memberService.findMemberByEmail(email);
        if (findMember == null) {
            return ResponseEntity.notFound().build();
        }
        return new ResponseEntity<>(findMember, OK);
    }

    private void checkHasBindingError(BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new RuntimeException(MEMBER_BINDING_ERROR);
        }
    }
}
