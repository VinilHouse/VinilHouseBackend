package com.ssafy.happyhouse5.dto.member;


import lombok.Getter;
import lombok.Setter;
import org.springframework.lang.Nullable;

@Getter
@Setter
public class MemberUpdateDto {
    @Nullable
    private String password;

    @Nullable
    private String email;
}
