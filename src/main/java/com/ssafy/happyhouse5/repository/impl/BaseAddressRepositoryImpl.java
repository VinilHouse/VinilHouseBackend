package com.ssafy.happyhouse5.repository.impl;

import static com.ssafy.happyhouse5.entity.QBaseAddress.baseAddress;
import static com.ssafy.happyhouse5.entity.QHouseInfo.houseInfo;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.ssafy.happyhouse5.dto.locationavg.LocationPriceDto;
import com.ssafy.happyhouse5.dto.locationavg.QLocationPriceDto;
import com.ssafy.happyhouse5.repository.BaseAddressCustom;
import java.util.List;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class BaseAddressRepositoryImpl implements BaseAddressCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public List<LocationPriceDto> findBySidoRange() {
        return queryFactory
            .select(new QLocationPriceDto(
                baseAddress.sidoName.as("name"),
                houseInfo.avgPrice.avg().as("avgPrice"),
                baseAddress.lat.avg(),
                baseAddress.lng.avg()))
            .from(baseAddress)
            .leftJoin(
                baseAddress.houseInfos, houseInfo)
            .groupBy(baseAddress.sidoName)
            .fetch();
    }

    @Override
    public List<LocationPriceDto> findByGugunRange(String sidoName) {
        return queryFactory
            .select(new QLocationPriceDto(
                baseAddress.gugunName.as("name"),
                houseInfo.avgPrice.avg().as("avgPrice"),
                baseAddress.lat.avg(),
                baseAddress.lng.avg()))
            .from(baseAddress)
            .leftJoin(
                baseAddress.houseInfos, houseInfo)
            .where(baseAddress.sidoName.eq(sidoName))
            .groupBy(baseAddress.gugunName)
            .fetch();
    }

    @Override
    public List<LocationPriceDto> findByDongRange(String gugunName) {
        return queryFactory
            .select(new QLocationPriceDto(
                baseAddress.dongName.as("name"),
                houseInfo.avgPrice.avg().as("avgPrice"),
                baseAddress.lat.avg(),
                baseAddress.lng.avg()))
            .from(baseAddress)
            .leftJoin(
                baseAddress.houseInfos, houseInfo)
            .where(baseAddress.gugunName.eq(gugunName))
            .groupBy(baseAddress.dongName)
            .fetch();
    }
}
