package com.ssafy.happyhouse5.service;

import com.ssafy.happyhouse5.dto.house.HouseDeal;
import com.ssafy.happyhouse5.dto.house.HouseInfo;
import java.sql.SQLException;
import java.util.List;

public interface HouseService {

    HouseInfo findHouseInfoByDongName(String dongName) throws SQLException;

    List<HouseDeal> findHouseDealListByAptCode(int aptCode) throws SQLException;
}
