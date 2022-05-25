package com.ssafy.happyhouse5.util.impl;

import com.ssafy.happyhouse5.util.KilometerConverter;
import org.springframework.stereotype.Component;

@Component
public class KilometerConverterImpl implements KilometerConverter {

    @Override
    public Double convert(Double lat1, Double lat2, Double lng1, Double lng2) {
        double lngDiff = Math.abs(lng1 - lng2);

        double dist = Math.sin(deg2rad(lat1)) * Math.sin(deg2rad(lat2))
            + Math.cos(deg2rad(lat1)) * Math.cos(deg2rad(lat2)) * Math.cos(deg2rad(lngDiff));
        dist = Math.acos(dist);
        dist = rad2deg(dist);
        return dist * 60 * 1.1515 * 1609.344 / 1000;
    }

    private static double deg2rad(double deg) {
        return (deg * Math.PI / 180.0);
    }

    private static double rad2deg(double rad) {
        return (rad * 180 / Math.PI);
    }
}
