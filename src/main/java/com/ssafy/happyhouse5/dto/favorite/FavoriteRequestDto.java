package com.ssafy.happyhouse5.dto.favorite;

import javax.validation.constraints.NotNull;
import lombok.Data;

@Data
public class FavoriteRequestDto {

    @NotNull
    private Long aptCode;
}
