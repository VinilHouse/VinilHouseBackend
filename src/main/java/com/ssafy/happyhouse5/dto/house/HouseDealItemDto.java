package com.ssafy.happyhouse5.dto.house;

import com.ssafy.happyhouse5.entity.HouseDeal;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class HouseDealItemDto implements Comparable<HouseDealItemDto> {

    private Long id;
    private Long aptCode;
    private Double dealAmount;
    private int dealYear;
    private int dealMonth;
    private int dealDay;
    private String area;
    private String floor;
    private String type;
    private String rentMoney;

    public HouseDealItemDto(HouseDeal houseDeal) {
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

    @Override
    public int compareTo(HouseDealItemDto o) {
        if (dealYear != o.dealYear) {
            return dealYear - o.dealYear;
        }
        if (dealMonth != o.dealMonth) {
            return dealMonth - o.dealMonth;
        }
        if(dealDay != o.dealDay){
            return dealDay - o.dealDay;
        }
        return 0;
    }
}
