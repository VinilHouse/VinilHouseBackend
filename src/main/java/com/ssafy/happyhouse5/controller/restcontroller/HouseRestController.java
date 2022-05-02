package com.ssafy.happyhouse5.controller.restcontroller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.ssafy.happyhouse5.dto.house.HouseDeal;
import com.ssafy.happyhouse5.dto.house.HouseInfo;
import com.ssafy.happyhouse5.service.HouseService;
import java.util.List;

@RestController
@RequestMapping("/house")
public class HouseRestController {

    private HouseService houseService;

    @Autowired
    public void setHouseService(HouseService houseService) {
        this.houseService = houseService;
    }

    @GetMapping("/infos")
    public List<HouseInfo> getHouseInfos(@RequestParam String dongCode) {
        return houseService.findHouseInfoByDongCode(dongCode);
    }

    @GetMapping("/deals")
    public List<HouseDeal> getHouseDeals(@RequestParam int aptCode) {
        return houseService.findHouseDealListByAptCode(aptCode);
    }
}
