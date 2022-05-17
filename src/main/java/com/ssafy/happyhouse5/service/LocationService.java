package com.ssafy.happyhouse5.service;

import com.ssafy.happyhouse5.dto.locationavg.LocationLevel;
import com.ssafy.happyhouse5.dto.locationavg.LocationPriceDto;
import java.util.List;

public interface LocationService {

    List<LocationPriceDto> getLocationPrice(LocationLevel level, String query);
}
