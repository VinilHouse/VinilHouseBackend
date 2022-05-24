package com.ssafy.happyhouse5.dto.house;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class HouseAreaGroup {

    private Integer py;
    private List<HouseDealItemDto> dealList;
}
