package com.ssafy.happyhouse5.service;


import com.ssafy.happyhouse5.entity.HouseDeal;
import com.ssafy.happyhouse5.entity.HouseInfo;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface HouseService {

    List<HouseInfo> findHouseInfoByDongName(String dongName);

    List<HouseDeal> findHouseDealListByAptCode(Long aptCode);

    Page<HouseInfo> findHouseInfoByPrefix(String prefix, Pageable pageable);
}
