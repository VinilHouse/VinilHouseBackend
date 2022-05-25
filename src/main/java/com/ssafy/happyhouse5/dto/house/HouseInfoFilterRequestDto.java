package com.ssafy.happyhouse5.dto.house;

import lombok.Data;

@Data
public class HouseInfoFilterRequestDto {

    private Double avgPriceBegin;
    private Double avgPriceEnd;
    private String yearBegin;
    private String yearEnd;

    public void convertToPyValue(){
        this.avgPriceBegin = Math.floor(avgPriceBegin / 3.30579);
        this.avgPriceEnd = Math.floor(avgPriceEnd / 3.30579);
    }
}
