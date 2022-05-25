package com.ssafy.happyhouse5.dto.member.location;

import lombok.Data;

@Data
public class MemberLocationUpdateDto {
    private Long id;
    private String alias;
    private String address;
}
