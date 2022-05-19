package com.ssafy.happyhouse5.service;

import com.ssafy.happyhouse5.dto.locationavg.LocationLevel;
import com.ssafy.happyhouse5.dto.locationavg.LocationPriceDto;
import com.ssafy.happyhouse5.dto.locationavg.LocationRange;
import java.util.List;

public interface LocationService {

    List<LocationPriceDto> getLocationPrice(LocationLevel level, String query);

    List<LocationPriceDto> getLocationPrice(LocationLevel level, LocationRange range);
}
