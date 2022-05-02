package com.ssafy.happyhouse5.dto.board;

import javax.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class BoardRegisterDto {
    @NotBlank
    private String title;

    @NotBlank
    private String content;
}
