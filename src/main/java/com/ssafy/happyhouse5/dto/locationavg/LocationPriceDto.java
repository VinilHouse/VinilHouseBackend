package com.ssafy.happyhouse5.dto.locationavg;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;

@Getter
public class LocationPriceDto {
    private final String name;
    private final Double avgPrice;
    private final Double lat;
    private final Double lng;

    @QueryProjection

    public LocationPriceDto(String name, Double avgPrice, Double lat, Double lng) {
        this.name = name;
        this.avgPrice = avgPrice;
        this.lat = lat;
        this.lng = lng;
    }
}
