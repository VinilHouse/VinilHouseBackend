package com.ssafy.happyhouse5.dto.house;

import lombok.Data;

@Data
public class HouseInfoFilterRequestDto {

    private Double avgPriceBegin;
    private Double avgPriceEnd;
    private String yearBegin;
    private String yearEnd;
}
