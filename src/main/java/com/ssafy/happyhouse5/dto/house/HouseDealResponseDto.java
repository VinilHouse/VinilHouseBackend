package com.ssafy.happyhouse5.dto.house;

import com.ssafy.happyhouse5.dto.comment.CommentResponseDto;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class HouseDealResponseDto {

    HouseInfoResponseDto houseInfoResponseDto;
    List<CommentResponseDto> commentResponseDtoList;
    List<HouseAreaGroup> groupList;
}
