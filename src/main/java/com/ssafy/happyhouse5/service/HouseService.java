package com.ssafy.happyhouse5.service;


import com.ssafy.happyhouse5.entity.HouseDeal;
import com.ssafy.happyhouse5.entity.HouseInfo;
import java.util.List;

public interface HouseService {

    List<HouseInfo> findHouseInfoByDongName(String dongCode);

    List<HouseDeal> findHouseDealListByAptCode(Long aptCode);
}
