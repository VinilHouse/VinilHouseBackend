package com.ssafy.happyhouse5.repository.impl;

import static com.ssafy.happyhouse5.entity.QFavorite.favorite;
import static com.ssafy.happyhouse5.entity.QHouseDeal.houseDeal;
import static com.ssafy.happyhouse5.entity.QHouseInfo.houseInfo;
import static org.springframework.util.StringUtils.hasText;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.NumberExpression;
import com.querydsl.core.types.dsl.NumberPath;
import com.querydsl.core.types.dsl.StringPath;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.ssafy.happyhouse5.dto.house.HouseInfoFilterRequestDto;
import com.ssafy.happyhouse5.dto.house.HouseInfoWithRankDto;
import com.ssafy.happyhouse5.dto.house.QHouseInfoWithRankDto;
import com.ssafy.happyhouse5.dto.locationavg.LocationRange;
import com.ssafy.happyhouse5.entity.HouseInfo;
import com.ssafy.happyhouse5.repository.HouseInfoCustom;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;

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

    @Override
    public List<HouseInfo> findHouseInfoInRange(
        LocationRange range,
        HouseInfoFilterRequestDto filter) {
        return queryFactory
            .selectFrom(houseInfo)
            .join(houseInfo.houseDeals, houseDeal)
            .where(
                betweenLatLng(houseInfo.lat, houseInfo.lng, range),
                betweenAvgPrice(filter.getAvgPriceBegin(), filter.getAvgPriceEnd(),
                    houseInfo.avgPrice),
                betweenYear(filter.getYearBegin(), filter.getYearEnd(), houseInfo.buildYear)
            )
            .fetch();
    }

    @Override
    public List<HouseInfoWithRankDto> findRankByFavorite(Pageable pageable) {
        return queryFactory
            .select(new QHouseInfoWithRankDto(houseInfo, houseInfo.count()))
            .from(favorite)
            .join(favorite.houseInfo, houseInfo)
            .groupBy(houseInfo)
            .orderBy(houseInfo.count().desc())
            .offset(pageable.getOffset())
            .limit(pageable.getPageSize())
            .fetch();
    }

    private BooleanExpression betweenLatLng(
        NumberExpression<Double> lat, NumberExpression<Double> lng, LocationRange range) {
        return lat.between(range.getBeginLat(), range.getEndLat())
            .and(lng.between(range.getBeginLng(), range.getEndLng()));
    }

    private BooleanExpression betweenAvgPrice(
        Double begin, Double end, NumberPath<Double> avgPrice) {
        if (begin == null || end == null) {
            return null;
        }
        return avgPrice.between(begin, end);
    }

    private BooleanExpression betweenYear(
        String begin, String end, StringPath buildYear) {
        if (!hasText(begin) || !hasText(end)) {
            return null;
        }
        return buildYear.between(begin, end);
    }
}
