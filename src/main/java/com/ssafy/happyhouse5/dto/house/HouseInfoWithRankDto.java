package com.ssafy.happyhouse5.dto.house;

import com.querydsl.core.annotations.QueryProjection;
import com.ssafy.happyhouse5.entity.HouseInfo;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class HouseInfoWithRankDto {

    private HouseInfoResponseDto houseInfoResponseDto;
    private Long popular;

    @QueryProjection
    public HouseInfoWithRankDto(HouseInfo houseInfo, Long popular) {
        this.houseInfoResponseDto = new HouseInfoResponseDto(houseInfo);
        this.popular = popular;
    }
}
