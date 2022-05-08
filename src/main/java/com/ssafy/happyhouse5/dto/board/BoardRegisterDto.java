package com.ssafy.happyhouse5.dto.board;

import javax.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BoardRegisterDto {
    @NotBlank
    private String title;

    @NotBlank
    private String content;
}
