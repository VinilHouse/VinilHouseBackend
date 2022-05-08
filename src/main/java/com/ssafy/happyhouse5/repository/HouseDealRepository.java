package com.ssafy.happyhouse5.repository;

import com.ssafy.happyhouse5.entity.HouseDeal;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface HouseDealRepository extends JpaRepository<HouseDeal, Long> {

    @Query("select d from HouseDeal d join fetch d.houseInfo i where i.aptCode = :aptCode")
    List<HouseDeal> findHouseDealByAptCode(Long aptCode);
}
