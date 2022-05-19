package com.ssafy.happyhouse5.repository;

import com.ssafy.happyhouse5.dto.locationavg.LocationRange;
import com.ssafy.happyhouse5.entity.HouseInfo;
import java.util.List;

public interface HouseInfoCustom {

    List<HouseInfo> findHouseInfoInRange(LocationRange range);
}
