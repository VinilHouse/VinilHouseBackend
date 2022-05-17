package com.ssafy.happyhouse5.entity;

import static lombok.AccessLevel.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = PROTECTED)
public class BaseAddress {

    @Id
    @Column(name = "dong_name")
    private String dongName;

    private String sidoName;

    private String gugunName;

    private String lat;

    private String lng;
}