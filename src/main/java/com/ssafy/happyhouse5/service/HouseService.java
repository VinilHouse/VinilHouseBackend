package com.ssafy.happyhouse5.service;


import com.ssafy.happyhouse5.dto.house.HouseInfoFilterRequestDto;
import com.ssafy.happyhouse5.dto.house.HouseInfoWithRankDto;
import com.ssafy.happyhouse5.dto.locationavg.LocationRange;
import com.ssafy.happyhouse5.entity.HouseDeal;
import com.ssafy.happyhouse5.entity.HouseInfo;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface HouseService {

    List<HouseInfo> findHouseInfoByDongName(String dongName);

    List<HouseDeal> findHouseDealListByAptCode(Long aptCode);

    Page<HouseInfo> findHouseInfoByPrefix(String prefix, Pageable pageable);

    List<HouseInfo> findHouseInfoInRange(LocationRange range);

    List<HouseInfo> findHouseInfoInRange(LocationRange range, HouseInfoFilterRequestDto filter);

    List<HouseInfoWithRankDto> findFavoriteRankHouseInfo(Pageable pageable);

    HouseInfo setIfImgNullAndGet(Long aptCode);
}
