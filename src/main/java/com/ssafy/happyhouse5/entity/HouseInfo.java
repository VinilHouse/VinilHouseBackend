package com.ssafy.happyhouse5.entity;

import static javax.persistence.FetchType.*;
import static lombok.AccessLevel.*;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.BatchSize;

@Entity
@Getter
@NoArgsConstructor(access = PROTECTED)
public class HouseInfo {

    @Id @GeneratedValue
    @Column(name = "apt_code")
    private Long aptCode;

    private String aptName;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "dong_name")
    private BaseAddress baseAddress;

    @BatchSize(size = 200)
    @OneToMany(mappedBy = "id")
    @OrderBy("dealYear desc, dealMonth desc, dealDay desc")
    private final List<HouseDeal> houseDeals = new ArrayList<>();

    @OneToMany(mappedBy = "houseInfo")
    private final List<Favorite> favorites = new ArrayList<>();

    private String buildYear;

    private String jibun;

    private Double lat;

    private Double lng;

    private String img;

    private Double avgPrice;

}
