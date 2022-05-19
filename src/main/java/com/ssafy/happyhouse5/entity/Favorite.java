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

@Entity
@Getter
public class Favorite {

    @Id @GeneratedValue
    @Column(name = "favorite_id")
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "apt_code")
    private HouseInfo houseInfo;

    public void setMember(Member member) {
        this.member = member;
        member.getFavorites().add(this);
    }

    public void setHouseInfo(HouseInfo houseInfo){
        this.houseInfo = houseInfo;
        houseInfo.getFavorites().add(this);
    }
}
