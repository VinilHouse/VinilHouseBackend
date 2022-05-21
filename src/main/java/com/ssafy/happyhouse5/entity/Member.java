package com.ssafy.happyhouse5.entity;

import static lombok.AccessLevel.*;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.BatchSize;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = PROTECTED)
@Table(
    name = "member",
    uniqueConstraints = {
        @UniqueConstraint(columnNames = {"ident"})
    })
public class Member {

    @Id
    @GeneratedValue
    @Column(name = "member_id")
    private Long id;

    private String ident;

    private String password;

    private String email;

    @OneToMany(mappedBy = "member")
    private final List<Board> boards = new ArrayList<>();

    @BatchSize(size = 30)
    @OneToMany(mappedBy = "member")
    private final List<Favorite> favorites = new ArrayList<>();

    public Member(String ident) {
        this.ident = ident;
    }
}
