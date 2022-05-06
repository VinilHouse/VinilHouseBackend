package com.ssafy.happyhouse5.entity;

import static lombok.AccessLevel.*;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = PROTECTED)
public class Member {

    @Id
    @Column(name = "member_id")
    private String id;

    private String password;

    private String email;

    @OneToMany(mappedBy = "member")
    private List<Board> boards = new ArrayList<>();
}
