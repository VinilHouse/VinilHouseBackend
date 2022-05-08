package com.ssafy.happyhouse5.entity;

import static javax.persistence.FetchType.*;
import static lombok.AccessLevel.*;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
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

    @Id
    @Column(name = "apt_code")
    private Long aptCode;

    private String aptName;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "dong_code")
    private BaseAddress baseAddress;

    private String buildYear;

    private String jibun;

    private String lat;

    private String lng;

    private String img;

    @BatchSize(size = 200)
    @OneToMany(mappedBy = "id")
    @OrderBy("dealYear desc, dealMonth desc, dealDay desc")
    List<HouseDeal> houseDeals = new ArrayList<>();
}
