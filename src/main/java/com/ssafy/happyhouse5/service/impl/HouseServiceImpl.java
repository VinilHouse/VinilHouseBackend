package com.ssafy.happyhouse5.service.impl;

import com.ssafy.happyhouse5.entity.HouseDeal;
import com.ssafy.happyhouse5.entity.HouseInfo;
import com.ssafy.happyhouse5.repository.HouseDealRepository;
import com.ssafy.happyhouse5.repository.HouseInfoRepository;
import com.ssafy.happyhouse5.service.HouseService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class HouseServiceImpl implements HouseService {

    private final HouseDealRepository houseDealRepository;

    private final HouseInfoRepository houseInfoRepository;

    @Override
    public List<HouseInfo> findHouseInfoByDongName(String dongName) {
        return houseInfoRepository.findHouseInfoBydongName(dongName);
    }

    @Override
    public List<HouseDeal> findHouseDealListByAptCode(Long aptCode) {
        return houseDealRepository.findHouseDealByAptCode(aptCode);
    }

    @Override
    public Page<HouseInfo> findHouseInfoByPrefix(String prefix, Pageable pageable) {
        return houseInfoRepository.findHouseInfoByAptPrefix(prefix, pageable);
    }
}
