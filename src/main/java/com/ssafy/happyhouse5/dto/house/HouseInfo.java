package com.ssafy.happyhouse5.dto.house;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class HouseInfo {

    private int aptCode;
    private String aptName;
    private String dongCode;
    private String dongName;
    private int buildYear;
    private String jibun;
    private String lat;
    private String lng;
    private String img;

}
