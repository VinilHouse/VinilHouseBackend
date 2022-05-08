package com.ssafy.happyhouse5.controller.restcontroller;

import static com.ssafy.happyhouse5.constant.MemberConst.*;
import static org.springframework.http.HttpStatus.*;

import com.ssafy.happyhouse5.dto.member.MemberLoginDto;
import com.ssafy.happyhouse5.dto.member.MemberRegisterDto;
import com.ssafy.happyhouse5.dto.member.MemberResponseDto;
import com.ssafy.happyhouse5.dto.member.MemberUpdateDto;
import com.ssafy.happyhouse5.entity.Member;
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
    public ResponseEntity<?> register(
        @Validated @RequestBody MemberRegisterDto memberRegisterDto,
        BindingResult bindingResult) {

        checkHasBindingError(bindingResult);

        memberService.register(memberRegisterDto);

        return ResponseEntity.created(URI.create("/api/members/" + memberRegisterDto.getIdent()))
            .build();
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(
        @RequestBody @Validated MemberLoginDto memberLoginDto,
        BindingResult bindingResult,
        HttpSession session) {

        checkHasBindingError(bindingResult);

        if (!memberService.login(memberLoginDto.getIdent(), memberLoginDto.getPassword())) {
            return ResponseEntity.status(UNAUTHORIZED).body(MEMBER_LOGIN_FAIL_MSG);
        }

        Member member = memberService.findMemberByIdent(memberLoginDto.getIdent());
        session.setAttribute(MEMBER_SESSION, member.getId());
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

    @PutMapping
    public ResponseEntity<?> update(
        @SessionAttribute(MEMBER_SESSION) Long memberId,
        @RequestBody MemberUpdateDto memberUpdateDto) {

        memberService.update(memberId, memberUpdateDto);
        return ResponseEntity.accepted().build();
    }

    @DeleteMapping
    public ResponseEntity<?> delete(HttpSession httpSession) {
        Long memberId = (Long) httpSession.getAttribute(MEMBER_SESSION);
        memberService.delete(memberId);
        httpSession.invalidate();
        return ResponseEntity.ok().build();
    }

    @GetMapping
    public ResponseEntity<MemberResponseDto> getMemberInfoBySession(
        @SessionAttribute(MEMBER_SESSION) Long memberId){
        return ResponseEntity.ok().body(
            new MemberResponseDto(memberService.findMemberById(memberId)));
    }

    @GetMapping("/id")
    public ResponseEntity<MemberResponseDto> findByIdent(@RequestParam(required = false) String ident) {
        Member findMember = memberService.findMemberByIdent(ident);
        if (findMember == null) {
            return ResponseEntity.notFound().build();
        }
        return new ResponseEntity<>(new MemberResponseDto(findMember), OK);
    }

    @GetMapping("/email")
    public ResponseEntity<MemberResponseDto> findByEmail(@RequestParam(required = false) String email) {
        Member findMember = memberService.findMemberByEmail(email);
        if (findMember == null) {
            return ResponseEntity.notFound().build();
        }
        return new ResponseEntity<>(new MemberResponseDto(findMember), OK);
    }

    private void checkHasBindingError(BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new RuntimeException(MEMBER_BINDING_ERROR);
        }
    }
}
