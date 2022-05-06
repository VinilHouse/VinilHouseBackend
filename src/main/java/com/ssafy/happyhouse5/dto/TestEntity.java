package com.ssafy.happyhouse5.dto;

import static lombok.AccessLevel.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = PROTECTED)
public class TestEntity {

    @Id @GeneratedValue
    private Long id;

    private String name;

    public TestEntity(String name) {
        this.name = name;
    }
}
