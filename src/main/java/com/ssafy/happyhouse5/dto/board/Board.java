package com.ssafy.happyhouse5.dto.board;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Board {

    @Setter(AccessLevel.NONE)
    private int id;
    private String title;
    private String content;
    private String memberId;
}
