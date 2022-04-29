package com.ssafy.happyhouse5.service.impl;

import com.ssafy.happyhouse5.dto.house.HouseDeal;
import com.ssafy.happyhouse5.dto.house.HouseInfo;
import com.ssafy.happyhouse5.service.HouseService;
import java.sql.SQLException;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class HouseServiceImpl implements HouseService {

    @Override
    public HouseInfo findHouseInfoByDongName(String dongName) throws SQLException {
        //Todo
        return null;
    }

    @Override
    public List<HouseDeal> findHouseDealListByAptCode(int aptCode) throws SQLException {
        //Todo
        return null;
    }
}
