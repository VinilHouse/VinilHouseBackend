package com.ssafy.happyhouse5.controller.restcontroller;

import static com.ssafy.happyhouse5.constant.MemberConst.*;
import static com.ssafy.happyhouse5.dto.common.Response.success;

import com.ssafy.happyhouse5.dto.common.Response;
import com.ssafy.happyhouse5.dto.house.HouseDealItemDto;
import com.ssafy.happyhouse5.dto.house.HouseDealResponseDto;
import com.ssafy.happyhouse5.dto.house.HouseInfoResponseDto;
import com.ssafy.happyhouse5.dto.locationavg.LocationRange;
import com.ssafy.happyhouse5.entity.HouseInfo;
import com.ssafy.happyhouse5.service.HouseService;
import com.ssafy.happyhouse5.service.MemberService;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.SessionAttribute;

@RequestMapping("/api/houses")
@RestController
@RequiredArgsConstructor
public class HouseRestController {

    private final HouseService houseService;

    private final MemberService memberService;

    @GetMapping("/info")
    public ResponseEntity<Response> getHouseInfos(@RequestParam String dongName) {
        return ResponseEntity.ok(
            success(houseService.findHouseInfoByDongName(dongName).stream()
                .map(HouseInfoResponseDto::new)
                .collect(Collectors.toList())
            ));
    }

    @GetMapping("/info/range")
    public ResponseEntity<Response> getHouseInfosInRange(@Validated LocationRange range) {
        return ResponseEntity.ok(
            success(houseService.findHouseInfoInRange(range).stream()
                .map(HouseInfoResponseDto::new)
                .collect(Collectors.toList())
            ));
    }

    @GetMapping("/deal")
    public ResponseEntity<Response> getHouseDeals(@RequestParam Long aptCode) {
        HouseInfo houseInfo = houseService.setIfImgNullAndGet(aptCode);
        List<HouseDealItemDto> houseDealItemDtoList =
            houseService.findHouseDealListByAptCode(aptCode).stream()
                .map(HouseDealItemDto::new)
                .collect(Collectors.toList());

        return ResponseEntity.ok(success(
            new HouseDealResponseDto(new HouseInfoResponseDto(houseInfo), houseDealItemDtoList)
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

    @GetMapping("/favorites")
    public ResponseEntity<Response> getFavoriteHouseInfoByMemberSession(
        @SessionAttribute(MEMBER_SESSION) Long memberId) {
        return ResponseEntity.ok(success(
            memberService.getFavoriteHouseInfo(memberId)
                .stream().map(HouseInfoResponseDto::new)
                .collect(Collectors.toList())));
    }

    @GetMapping("/rank")
    public ResponseEntity<Response> getTopFavoriteRankHouseInfos() {
        return ResponseEntity.ok(success(
            houseService.findFavoriteRankHouseInfo(PageRequest.of(0, 10)))
        );
    }
}
