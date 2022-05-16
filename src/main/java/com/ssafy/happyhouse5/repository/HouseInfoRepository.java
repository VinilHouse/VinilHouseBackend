package com.ssafy.happyhouse5.repository;

import com.ssafy.happyhouse5.entity.HouseInfo;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface HouseInfoRepository extends JpaRepository<HouseInfo, Long> {

    @Query("select i from HouseInfo i join fetch i.baseAddress b where b.dongName = :dongName")
    List<HouseInfo> findHouseInfoBydongName(String dongName);
}
