package com.ssafy.happyhouse5.entity;

import static javax.persistence.FetchType.*;
import static lombok.AccessLevel.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import lombok.Getter;
import lombok.NoArgsConstructor;

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
}
