package com.ssafy.happyhouse5.dto.locationavg;

import javax.validation.constraints.NotNull;
import lombok.Data;

@Data
public class LocationRange {

    @NotNull
    Double beginLat;

    @NotNull
    Double beginLng;

    @NotNull
    Double endLat;

    @NotNull
    Double endLng;
}
