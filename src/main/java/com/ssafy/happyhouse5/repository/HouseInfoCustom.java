package com.ssafy.happyhouse5.repository;

import com.ssafy.happyhouse5.dto.house.HouseInfoWithRankDto;
import com.ssafy.happyhouse5.dto.locationavg.LocationRange;
import com.ssafy.happyhouse5.entity.HouseInfo;
import java.util.List;
import org.springframework.data.domain.Pageable;

public interface HouseInfoCustom {

    List<HouseInfo> findHouseInfoInRange(LocationRange range);

    List<HouseInfoWithRankDto> findRankByFavorite(Pageable pageable);
}
