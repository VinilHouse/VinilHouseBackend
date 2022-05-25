package com.ssafy.happyhouse5.dto.member.dist;

import com.ssafy.happyhouse5.dto.house.HouseInfoResponseDto;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class DistDto {

    private HouseInfoResponseDto houseInfo;
    private List<DistGroup> groups;
}
