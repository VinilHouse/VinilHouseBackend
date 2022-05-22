package com.ssafy.happyhouse5.repository.impl;

import static com.ssafy.happyhouse5.entity.QBaseAddress.baseAddress;
import static com.ssafy.happyhouse5.entity.QHouseInfo.houseInfo;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.NumberExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.ssafy.happyhouse5.dto.locationavg.LocationPriceDto;
import com.ssafy.happyhouse5.dto.locationavg.LocationRange;
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

    @Override
    public List<LocationPriceDto> findAllBySidoInRange(LocationRange range) {
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
            .having(betweenLatLng(baseAddress.lat, baseAddress.lng, range))
            .fetch();
    }

    @Override
    public List<LocationPriceDto> findAllByGugunInRange(LocationRange range) {
        return queryFactory
            .select(new QLocationPriceDto(
                baseAddress.gugunName.as("name"),
                houseInfo.avgPrice.avg().as("avgPrice"),
                baseAddress.lat.avg(),
                baseAddress.lng.avg()))
            .from(baseAddress)
            .leftJoin(
                baseAddress.houseInfos, houseInfo)
            .groupBy(baseAddress.gugunName)
            .having(betweenLatLng(baseAddress.lat, baseAddress.lng, range))
            .fetch();
    }

    @Override
    public List<LocationPriceDto> findAllByDongInRange(LocationRange range) {
        return queryFactory
            .select(new QLocationPriceDto(
                baseAddress.dongName.as("name"),
                houseInfo.avgPrice.avg().as("avgPrice"),
                baseAddress.lat.avg(),
                baseAddress.lng.avg()))
            .from(baseAddress)
            .leftJoin(
                baseAddress.houseInfos, houseInfo)
            .groupBy(baseAddress.dongName)
            .having(betweenLatLng(baseAddress.lat, baseAddress.lng, range))
            .fetch();
    }

    private BooleanExpression betweenLatLng(
        NumberExpression<Double> lat, NumberExpression<Double> lng, LocationRange range) {
        return lat.between(range.getBeginLat(), range.getEndLat())
            .and(lng.between(range.getBeginLng(), range.getEndLng()));
    }
}
