package com.ssafy.happyhouse5.repository;

import com.ssafy.happyhouse5.dto.locationavg.LocationPriceDto;
import com.ssafy.happyhouse5.dto.locationavg.LocationRange;
import java.util.List;

public interface BaseAddressCustom {

    List<LocationPriceDto> findBySidoRange();

    List<LocationPriceDto> findByGugunRange(String sidoName);

    List<LocationPriceDto> findByDongRange(String gugunName);

    List<LocationPriceDto> findAllBySidoInRange(LocationRange range);

    List<LocationPriceDto> findAllByGugunInRange(LocationRange range);

    List<LocationPriceDto> findAllByDongInRange(LocationRange range);
}
