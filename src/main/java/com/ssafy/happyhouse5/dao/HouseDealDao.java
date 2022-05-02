package com.ssafy.happyhouse5.dao;

import com.ssafy.happyhouse5.dto.house.HouseDeal;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface HouseDealDao {

    List<HouseDeal> selectHouseDealList(int aptCode);

}
