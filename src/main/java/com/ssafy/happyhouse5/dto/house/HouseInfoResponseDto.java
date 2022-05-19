package com.ssafy.happyhouse5.dto.house;

import com.ssafy.happyhouse5.entity.HouseInfo;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.util.StringUtils;

@Getter
@Setter
@ToString
public class HouseInfoResponseDto {

    private Long aptCode;
    private String aptName;
    private String dongName;
    private int buildYear;
    private String jibun;
    private Double lat;
    private Double lng;
    private String img;
    private Double avgPrice;

    public HouseInfoResponseDto(HouseInfo houseInfo) {
        this.aptCode = houseInfo.getAptCode();
        this.aptName = houseInfo.getAptName();
        this.dongName = houseInfo.getBaseAddress().getDongName();
        if (StringUtils.hasText(houseInfo.getBuildYear())) {
            this.buildYear = Integer.parseInt(houseInfo.getBuildYear());
        }
        this.jibun = houseInfo.getJibun();
        this.lat = houseInfo.getLat();
        this.lng = houseInfo.getLng();
        this.img = houseInfo.getImg();
        this.avgPrice = houseInfo.getAvgPrice();
    }
}
