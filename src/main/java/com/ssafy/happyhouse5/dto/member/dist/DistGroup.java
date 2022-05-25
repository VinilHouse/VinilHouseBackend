package com.ssafy.happyhouse5.dto.member.dist;

import com.ssafy.happyhouse5.dto.member.location.MemberLocationResponse;
import lombok.Data;

@Data
public class DistGroup {
    private Double dist;
    private MemberLocationResponse memberLocation;

    public DistGroup(MemberLocationResponse memberLocation) {
        this.memberLocation = memberLocation;
    }
}
