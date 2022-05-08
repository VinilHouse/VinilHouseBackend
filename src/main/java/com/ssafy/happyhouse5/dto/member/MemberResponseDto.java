package com.ssafy.happyhouse5.dto.member;

import com.ssafy.happyhouse5.entity.Member;
import lombok.Getter;

@Getter
public class MemberResponseDto {
    String ident;
    String password;
    String email;

    public MemberResponseDto(Member member) {
        this.ident = member.getIdent();
        this.password = member.getPassword();
        this.email = member.getPassword();
    }
}
