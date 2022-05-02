package com.ssafy.happyhouse5.service;

import com.ssafy.happyhouse5.dto.house.HouseDeal;
import com.ssafy.happyhouse5.dto.house.HouseInfo;
import java.util.List;

public interface HouseService {

    List<HouseInfo> findHouseInfoByDongCode(String dongCode);

    List<HouseDeal> findHouseDealListByAptCode(int aptCode);
}
