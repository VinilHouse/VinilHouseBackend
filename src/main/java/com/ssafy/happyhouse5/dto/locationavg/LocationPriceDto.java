package com.ssafy.happyhouse5.dto.locationavg;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;

@Getter
public class LocationPriceDto {
    private final String name;
    private final Double avgPrice;

    @QueryProjection
    public LocationPriceDto(String name, Double avgPrice) {
        this.name = name;
        this.avgPrice = avgPrice;
    }
}
