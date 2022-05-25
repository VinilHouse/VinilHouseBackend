package com.ssafy.happyhouse5.service.impl;

import static com.ssafy.happyhouse5.constant.HouseConst.*;
import static org.springframework.util.StringUtils.*;

import com.ssafy.happyhouse5.dto.house.HouseInfoFilterRequestDto;
import com.ssafy.happyhouse5.dto.house.HouseInfoWithRankDto;
import com.ssafy.happyhouse5.dto.locationavg.LocationRange;
import com.ssafy.happyhouse5.entity.HouseDeal;
import com.ssafy.happyhouse5.entity.HouseInfo;
import com.ssafy.happyhouse5.exception.house.HouseInfoNotFoundException;
import com.ssafy.happyhouse5.repository.HouseDealRepository;
import com.ssafy.happyhouse5.repository.HouseInfoRepository;
import com.ssafy.happyhouse5.service.HouseService;
import com.ssafy.happyhouse5.util.HouseInfoImgSearchUtil;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class HouseServiceImpl implements HouseService {

    private final HouseDealRepository houseDealRepository;

    private final HouseInfoRepository houseInfoRepository;

    private final HouseInfoImgSearchUtil houseInfoImgSearchUtil;

    @Override
    public List<HouseInfo> findHouseInfoByDongName(String dongName) {
        return houseInfoRepository.findHouseInfoBydongName(dongName);
    }

    @Override
    public List<HouseDeal> findHouseDealListByAptCode(Long aptCode) {
        return houseDealRepository.findHouseDealByAptCode(aptCode);
    }

    @Override
    public Page<HouseInfo> findHouseInfoByPrefix(String prefix, Pageable pageable) {
        return houseInfoRepository.findHouseInfoByAptPrefix(prefix, pageable);
    }

    @Override
    public List<HouseInfo> findHouseInfoInRange(LocationRange range) {
        return houseInfoRepository.findHouseInfoInRange(range);
    }

    @Override
    public List<HouseInfo> findHouseInfoInRange(
        LocationRange range, HouseInfoFilterRequestDto filter) {
        return houseInfoRepository.findHouseInfoInRange(range, filter);
    }

    @Override
    public List<HouseInfoWithRankDto> findFavoriteRankHouseInfo(Pageable pageable) {
        return houseInfoRepository.findRankByFavorite(pageable);
    }

    @Override
    @Transactional
    public HouseInfo setIfImgNullAndGet(Long aptCode) {
        HouseInfo houseInfo = checkExistAndGetHouseInfoByAptCode(aptCode);
        setImageByExternalApi(houseInfo);
        return houseInfo;
    }

    private void setImageByExternalApi(HouseInfo houseInfo) {
        if (!hasText(houseInfo.getImg())) {
            String imgLink = houseInfoImgSearchUtil.getImgLink(houseInfo.getAptName());
            if (hasText(imgLink)) {
                houseInfo.setImg(imgLink);
            } else {
                houseInfo.setImg(HOUSE_INFO_IMG_NULL);
            }
        }
    }

    private HouseInfo checkExistAndGetHouseInfoByAptCode(Long aptCode) {
        return houseInfoRepository.findById(aptCode)
            .orElseThrow(HouseInfoNotFoundException::new);
    }
}
