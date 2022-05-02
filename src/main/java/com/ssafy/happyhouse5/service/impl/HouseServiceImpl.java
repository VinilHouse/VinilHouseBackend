package com.ssafy.happyhouse5.service.impl;

import com.ssafy.happyhouse5.dao.HouseDealDao;
import com.ssafy.happyhouse5.dao.HouseInfoDao;
import com.ssafy.happyhouse5.dto.house.HouseDeal;
import com.ssafy.happyhouse5.dto.house.HouseInfo;
import com.ssafy.happyhouse5.service.HouseService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class HouseServiceImpl implements HouseService {

    private final HouseInfoDao houseInfoDao;
    private final HouseDealDao houseDealDao;

    @Override
    public List<HouseInfo> findHouseInfoByDongCode(String dongCode) {
        return houseInfoDao.selectHouseList(dongCode);
    }

    @Override
    public List<HouseDeal> findHouseDealListByAptCode(int aptCode) {
        return houseDealDao.selectHouseDealList(aptCode);
    }
}
