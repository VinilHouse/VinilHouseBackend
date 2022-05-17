package com.ssafy.happyhouse5.controller.restcontroller;

import com.ssafy.happyhouse5.dto.house.HouseDealResponseDto;
import com.ssafy.happyhouse5.dto.house.HouseInfoResponseDto;
import com.ssafy.happyhouse5.entity.HouseDeal;
import com.ssafy.happyhouse5.entity.HouseInfo;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.ssafy.happyhouse5.service.HouseService;

@RequestMapping("/api/houses")
@RestController
@RequiredArgsConstructor
public class HouseRestController {

    private final HouseService houseService;

    @GetMapping("/info")
    public ResponseEntity<List<HouseInfoResponseDto>> getHouseInfos(@RequestParam String dongName) {
        List<HouseInfo> houseInfoList = houseService.findHouseInfoByDongName(dongName);
        if (houseInfoList.size() == 0) {
            return new ResponseEntity<>(new ArrayList<>(), HttpStatus.OK);
        }
        return ResponseEntity.ok(houseInfoList.stream()
            .map(HouseInfoResponseDto::new)
            .collect(Collectors.toList()));
    }

    @GetMapping("/deal")
    public ResponseEntity<List<HouseDealResponseDto>> getHouseDeals(@RequestParam Long aptCode) {
        List<HouseDeal> houseDealList = houseService.findHouseDealListByAptCode(aptCode);
        if (houseDealList == null) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(houseDealList.stream()
            .map(HouseDealResponseDto::new)
            .collect(Collectors.toList()));
    }

    @GetMapping("/search")
    public ResponseEntity<List<HouseInfoResponseDto>> searchHouseInfos(@RequestParam String prefix){
        Pageable pageable = PageRequest.of(0, 20);
        return ResponseEntity.ok(houseService.findHouseInfoByPrefix(prefix, pageable)
            .map(HouseInfoResponseDto::new)
            .toList());
    }
}
