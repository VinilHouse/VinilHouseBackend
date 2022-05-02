package com.ssafy.happyhouse5.dto.house;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
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
