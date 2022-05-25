package com.ssafy.happyhouse5.dto.house;

import java.util.Set;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class HouseAreaGroup implements Comparable<HouseAreaGroup> {

    private Integer py;
    private Set<HouseDealItemDto> dealList;

    @Override
    public int compareTo(HouseAreaGroup o) {
        return py.compareTo(o.py);
    }
}
