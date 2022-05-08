package com.ssafy.happyhouse5.service.impl;

import com.ssafy.happyhouse5.entity.HouseDeal;
import com.ssafy.happyhouse5.entity.HouseInfo;
import com.ssafy.happyhouse5.repository.HouseDealRepository;
import com.ssafy.happyhouse5.repository.HouseInfoRepository;
import com.ssafy.happyhouse5.service.HouseService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class HouseServiceImpl implements HouseService {

    private final HouseDealRepository houseDealRepository;

    private final HouseInfoRepository houseInfoRepository;

    @Override
    public List<HouseInfo> findHouseInfoByDongCode(String dongCode) {
        return houseInfoRepository.findHouseInfoByDongCode(dongCode);
    }

    @Override
    public List<HouseDeal> findHouseDealListByAptCode(Long aptCode) {
        return houseDealRepository.findHouseDealByAptCode(aptCode);
    }
}
