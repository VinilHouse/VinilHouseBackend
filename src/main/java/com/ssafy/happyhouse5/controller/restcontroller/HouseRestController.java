package com.ssafy.happyhouse5.controller.restcontroller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.ssafy.happyhouse5.dto.house.HouseDeal;
import com.ssafy.happyhouse5.dto.house.HouseInfo;
import com.ssafy.happyhouse5.service.HouseService;
import java.util.List;

@RestController
@RequestMapping("/api/house")
public class HouseRestController {

    private HouseService houseService;

    @Autowired
    public void setHouseService(HouseService houseService) {
        this.houseService = houseService;
    }

    @GetMapping("/infos")
    public ResponseEntity<List<HouseInfo>> getHouseInfos(@RequestParam String dongCode) {
        List<HouseInfo> houseInfoList = houseService.findHouseInfoByDongCode(dongCode);
        if (houseInfoList == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(houseInfoList);
    }

    @GetMapping("/deals")
    public ResponseEntity<List<HouseDeal>> getHouseDeals(@RequestParam int aptCode) {
        List<HouseDeal> houseDealList = houseService.findHouseDealListByAptCode(aptCode);
        if (houseDealList == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(houseDealList);
    }
}
