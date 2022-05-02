package com.ssafy.happyhouse5.dao;

import com.ssafy.happyhouse5.dto.house.HouseInfo;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface HouseInfoDao {

    List<HouseInfo> selectHouseList(String dongCode);
}
