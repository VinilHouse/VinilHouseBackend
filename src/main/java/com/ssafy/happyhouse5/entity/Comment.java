package com.ssafy.happyhouse5.entity;

import static javax.persistence.FetchType.LAZY;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "comment")
@NoArgsConstructor
public class Comment {

    @Id @GeneratedValue
    @Column(name = "comment_id")
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "apt_code")
    private HouseInfo houseInfo;

    private String title;

    private String content;

    public void setMember(Member member){
        this.member = member;
    }

    public void setHouseInfo(HouseInfo houseInfo){
        this.houseInfo = houseInfo;
        houseInfo.getComments().add(this);
    }
}
