package com.ssafy.happyhouse5.dto.member;

import javax.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class MemberRegisterDto {

    @NotBlank
    private String ident;

    @NotBlank
    private String password;

    @NotBlank
    private String email;
}
