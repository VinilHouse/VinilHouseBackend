package com.ssafy.happyhouse5.dto.board;

import javax.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class BoardUpdateDto {
    @NotNull
    private String title;

    @NotNull
    private String content;
}
