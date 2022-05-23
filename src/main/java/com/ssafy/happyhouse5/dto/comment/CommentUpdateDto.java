package com.ssafy.happyhouse5.dto.comment;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CommentUpdateDto {

    @NotNull
    private Long commentId;

    @NotBlank
    private String title;

    @NotBlank
    private String content;
}
