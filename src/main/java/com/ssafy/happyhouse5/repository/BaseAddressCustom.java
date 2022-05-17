package com.ssafy.happyhouse5.repository;

import com.ssafy.happyhouse5.dto.locationavg.LocationPriceDto;
import java.util.List;

public interface BaseAddressCustom {

    List<LocationPriceDto> findBySidoRange();

    List<LocationPriceDto> findByGugunRange(String sidoName);

    List<LocationPriceDto> findByDongRange(String gugunName);
}
