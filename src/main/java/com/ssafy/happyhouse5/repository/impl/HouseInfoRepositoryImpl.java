package com.ssafy.happyhouse5.repository.impl;

import static com.ssafy.happyhouse5.entity.QHouseInfo.houseInfo;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.NumberExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.ssafy.happyhouse5.dto.locationavg.LocationRange;
import com.ssafy.happyhouse5.entity.HouseInfo;
import com.ssafy.happyhouse5.repository.HouseInfoCustom;
import java.util.List;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class HouseInfoRepositoryImpl implements HouseInfoCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public List<HouseInfo> findHouseInfoInRange(LocationRange range) {
        return queryFactory
            .select(houseInfo)
            .from(houseInfo)
            .where(betweenLatLng(houseInfo.lat, houseInfo.lng, range))
            .fetch();
    }

    private BooleanExpression betweenLatLng(
        NumberExpression<Double> lat, NumberExpression<Double> lng, LocationRange range) {
        return lat.between(range.getBeginLat(), range.getEndLat())
            .and(lng.between(range.getBeginLng(), range.getEndLng()));
    }
}
