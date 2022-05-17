package com.ssafy.happyhouse5.service.impl;

import com.ssafy.happyhouse5.dto.locationavg.LocationLevel;
import com.ssafy.happyhouse5.dto.locationavg.LocationPriceDto;
import com.ssafy.happyhouse5.repository.BaseAddressRepository;
import com.ssafy.happyhouse5.service.LocationService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class LocationServiceImpl implements LocationService {

    private final BaseAddressRepository baseAddressRepository;

    @Override
    public List<LocationPriceDto> getLocationPrice(LocationLevel level, String query) {
        switch(level){
            case SIDO:
                return baseAddressRepository.findBySidoRange();
            case GUGUN:
                return baseAddressRepository.findByGugunRange(query);
            case DONG:
                return baseAddressRepository.findByDongRange(query);
        }
        throw new RuntimeException("서버 내부 오류가 발생했습니다.");
    }
}
