package com.ssafy.happyhouse5.dto.baseaddress;

import com.ssafy.happyhouse5.entity.BaseAddress;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@Builder
@AllArgsConstructor
public class BaseAddressDto {

    private String sidoName;
    private String gugunName;
    private String dongName;
    private String lat;
    private String lng;

    public BaseAddressDto(BaseAddress baseAddress) {
        this.sidoName = baseAddress.getSidoName();
        this.gugunName = baseAddress.getGugunName();
        this.dongName = baseAddress.getDongName();
        this.lat = baseAddress.getLat();
        this.lng = baseAddress.getLng();
    }
}
