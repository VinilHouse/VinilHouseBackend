package com.ssafy.happyhouse5.dto.member;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class MemberSession {
    private String id;
    private String email;
}
