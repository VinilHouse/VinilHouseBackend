package com.ssafy.happyhouse5.dto.member;

import javax.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class MemberLoginDto {

    @NotBlank
    private String id;

    @NotBlank
    private String password;
}
