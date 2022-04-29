package com.ssafy.happyhouse5.controller.restcontroller;

import static com.ssafy.happyhouse5.constant.MemberConst.*;

import com.ssafy.happyhouse5.dto.member.Member;
import com.ssafy.happyhouse5.dto.member.MemberLoginDto;
import com.ssafy.happyhouse5.dto.member.MemberRegisterDto;
import com.ssafy.happyhouse5.dto.member.MemberSession;
import com.ssafy.happyhouse5.service.MemberService;
import java.net.URI;
import javax.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
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
        HttpServletRequest request) {

        checkHasBindingError(bindingResult);

        if (memberService.login(memberLoginDto.getId(), memberLoginDto.getPassword())) {
            Member member = memberService.findMemberById(memberLoginDto.getId());
            request.getSession().setAttribute(MEMBER_SESSION, MemberSession.builder()
                .id(member.getId())
                .email(member.getEmail())
                .build());
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(MEMBER_LOGIN_FAIL_MSG);
    }

    private void checkHasBindingError(BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new RuntimeException(MEMBER_BINDING_ERROR);
        }
    }
}
