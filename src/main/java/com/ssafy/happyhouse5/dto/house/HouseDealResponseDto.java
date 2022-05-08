package com.ssafy.happyhouse5.dto.house;

import com.ssafy.happyhouse5.entity.HouseDeal;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class HouseDealResponseDto {

    private Long id;
    private Long aptCode;
    private String dealAmount;
    private int dealYear;
    private int dealMonth;
    private int dealDay;
    private String area;
    private String floor;
    private String type;
    private String rentMoney;

    public HouseDealResponseDto(HouseDeal houseDeal) {
        this.id = houseDeal.getId();
        this.aptCode = houseDeal.getHouseInfo().getAptCode();
        this.dealAmount = houseDeal.getDealAmount();
        this.dealYear = houseDeal.getDealYear();
        this.dealMonth = houseDeal.getDealMonth();
        this.dealDay = houseDeal.getDealDay();
        this.area = houseDeal.getArea();
        this.floor = houseDeal.getFloor();
        this.type = houseDeal.getType();
        this.rentMoney = houseDeal.getRentMoney();
    }
}
