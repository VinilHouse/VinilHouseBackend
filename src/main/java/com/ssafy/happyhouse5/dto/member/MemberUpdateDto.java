package com.ssafy.happyhouse5.dto.member;


import lombok.Getter;
import org.springframework.lang.Nullable;

@Getter
public class MemberUpdateDto {
    @Nullable
    private String password;

    @Nullable
    private String email;
}
