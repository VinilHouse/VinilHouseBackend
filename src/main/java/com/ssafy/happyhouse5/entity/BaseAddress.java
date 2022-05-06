package com.ssafy.happyhouse5.entity;

import static lombok.AccessLevel.*;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = PROTECTED)
@Table(name = "baseaddress")
public class BaseAddress {

    @Id
    private String dongName;

    private String sidoName;

    private String gugunName;

    private String lat;

    private String lng;
}
