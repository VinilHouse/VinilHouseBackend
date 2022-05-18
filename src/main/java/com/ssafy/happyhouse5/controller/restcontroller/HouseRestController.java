package com.ssafy.happyhouse5.controller.restcontroller;

import static com.ssafy.happyhouse5.dto.common.Response.success;

import com.ssafy.happyhouse5.dto.common.Response;
import com.ssafy.happyhouse5.dto.house.HouseDealResponseDto;
import com.ssafy.happyhouse5.dto.house.HouseInfoResponseDto;
import com.ssafy.happyhouse5.entity.HouseDeal;
import com.ssafy.happyhouse5.entity.HouseInfo;
import com.ssafy.happyhouse5.service.HouseService;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/api/houses")
@RestController
@RequiredArgsConstructor
public class HouseRestController {

    private final HouseService houseService;

    @GetMapping("/info")
    public ResponseEntity<Response> getHouseInfos(@RequestParam String dongName) {
        List<HouseInfo> houseInfoList = houseService.findHouseInfoByDongName(dongName);
        if (houseInfoList.size() == 0) {
            return ResponseEntity.ok(success(new ArrayList<>()));
        }
        return ResponseEntity.ok(success(houseInfoList.stream()
            .map(HouseInfoResponseDto::new)
            .collect(Collectors.toList())
        ));
    }

    @GetMapping("/deal")
    public ResponseEntity<Response> getHouseDeals(@RequestParam Long aptCode) {
        List<HouseDeal> houseDealList = houseService.findHouseDealListByAptCode(aptCode);
        if (houseDealList == null) {
            return ResponseEntity.ok(success());
        }
        return ResponseEntity.ok(success(houseDealList.stream()
            .map(HouseDealResponseDto::new)
            .collect(Collectors.toList())
        ));
    }

    @GetMapping("/search")
    public ResponseEntity<Response> searchHouseInfos(
        @RequestParam String prefix) {
        Pageable pageable = PageRequest.of(0, 20);
        return ResponseEntity.ok(
            success(houseService.findHouseInfoByPrefix(prefix, pageable)
                .map(HouseInfoResponseDto::new)
                .toList())
        );
    }
}
