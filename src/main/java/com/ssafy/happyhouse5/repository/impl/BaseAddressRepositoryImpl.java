package com.ssafy.happyhouse5.repository.impl;

import com.ssafy.happyhouse5.dto.locationavg.LocationPriceDto;
import com.ssafy.happyhouse5.repository.BaseAddressCustom;
import java.util.List;

public class BaseAddressRepositoryImpl implements BaseAddressCustom {

    @Override
    public List<LocationPriceDto> findBySidoRange() {
        return null;
    }

    @Override
    public List<LocationPriceDto> findByGugunRange(String sidoName) {
        return null;
    }

    @Override
    public List<LocationPriceDto> findByDongRange(String gugunName) {
        return null;
    }
}
