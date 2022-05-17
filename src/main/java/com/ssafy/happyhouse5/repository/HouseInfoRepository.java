package com.ssafy.happyhouse5.repository;

import com.ssafy.happyhouse5.entity.HouseInfo;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface HouseInfoRepository extends JpaRepository<HouseInfo, Long> {

    @Query("select i from HouseInfo i join fetch i.baseAddress b where b.dongName = :dongName")
    List<HouseInfo> findHouseInfoBydongName(@Param("dongName") String dongName);

    @Query("select i from HouseInfo i where i.aptName like :prefix%")
    Page<HouseInfo> findHouseInfoByAptPrefix(@Param("prefix")String prefix, Pageable pageable);
}
