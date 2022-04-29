package com.ssafy.happyhouse5.dto.house;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class HouseDeal {

    private int no;
    private int aptCode;
    private String dealAmount;
    private int dealYear;
    private int dealMonth;
    private int dealDay;
    private String area;
    private String floor;
    private String type;
    private String rentMoney;

}
