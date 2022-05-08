package com.ssafy.happyhouse5.entity;

import static javax.persistence.FetchType.*;
import static lombok.AccessLevel.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = PROTECTED)
public class HouseDeal {

    @Id @GeneratedValue
    @Column(name = "house_deal_id")
    private Long id;

    @JoinColumn(name = "apt_code")
    @ManyToOne(fetch = LAZY)
    private HouseInfo houseInfo;

    private String dealAmount;

    private Integer dealYear;

    private Integer dealMonth;

    private Integer dealDay;

    private String area;

    private String floor;

    private String type;

    private String rentMoney;
}
