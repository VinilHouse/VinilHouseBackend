package com.ssafy.happyhouse5.dto.member.location;

import com.ssafy.happyhouse5.entity.MemberLocation;
import lombok.Data;

@Data
public class MemberLocationResponse {
    private Long id;
    private String alias;
    private String address;
    private Double lat;
    private Double lng;

    public MemberLocationResponse(MemberLocation memberLocation) {
        this.id = memberLocation.getId();
        this.alias = memberLocation.getAlias();
        this.address = memberLocation.getAddress();
        this.lat = memberLocation.getLat();
        this.lng = memberLocation.getLng();
    }
}
