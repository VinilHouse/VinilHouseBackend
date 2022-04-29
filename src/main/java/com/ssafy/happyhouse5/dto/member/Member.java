package com.ssafy.happyhouse5.dto.member;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Member {

    @Setter(AccessLevel.NONE)
    private String id;
    private String password;
    private String email;
}
