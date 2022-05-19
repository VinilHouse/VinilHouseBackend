package com.ssafy.happyhouse5.controller.restcontroller;

import static com.ssafy.happyhouse5.dto.common.Response.success;
import static com.ssafy.happyhouse5.dto.locationavg.LocationLevel.SIDO;

import com.ssafy.happyhouse5.dto.common.Response;
import com.ssafy.happyhouse5.dto.locationavg.LocationLevel;
import com.ssafy.happyhouse5.dto.locationavg.LocationRange;
import com.ssafy.happyhouse5.exception.common.BadParamException;
import com.ssafy.happyhouse5.service.LocationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequestMapping("/api/location")
@RestController
@RequiredArgsConstructor
public class LocationAvgController {

    private final LocationService locationService;

    @GetMapping
    public ResponseEntity<Response> getLocationAndAvgPrice(
        @RequestParam LocationLevel level,
        @RequestParam(required = false) String query) {

        log.debug("level : {}, query : {}", level, query);
        checkParamValid(level, query);

        return ResponseEntity.ok(success(locationService.getLocationPrice(level, query)));
    }

    @GetMapping("/range")
    public ResponseEntity<Response> getLocationAndAvgPriceByRange(
        @RequestParam LocationLevel level,
        @Validated LocationRange range) {

        return ResponseEntity.ok(success(locationService.getLocationPrice(level, range)));
    }

    private void checkParamValid(LocationLevel level, String query) {
        if (level != SIDO && !StringUtils.hasText(query)) {
            throw new BadParamException();
        }
    }
}
