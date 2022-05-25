package com.ssafy.happyhouse5.util.impl;

import com.ssafy.happyhouse5.exception.member.MemberLocationConvertException;
import com.ssafy.happyhouse5.util.AddressConverter;
import com.ssafy.happyhouse5.util.LatLng;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

@Component
public class AddressConverterImpl implements AddressConverter {

    @Value("${url.geo.converter}")
    private String GEO_URL;

    @Value("${key.kakao}")
    private String KEY;

    public LatLng convert(String address) {
        Object searchBody = requestAndGetBody(GEO_URL + address);
        LatLng latLng = parseAndGetLatLng(searchBody);
        if (latLng == null) {
            throw new MemberLocationConvertException();
        }
        return latLng;
    }

    private Object requestAndGetBody(String url) {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders header = new HttpHeaders();
        header.set("Authorization", KEY);
        HttpEntity<?> entity = new HttpEntity<>(header);

        UriComponents uri = UriComponentsBuilder.fromHttpUrl(url).build();
        ResponseEntity<?> resultMap = restTemplate.exchange(
            uri.toString(), HttpMethod.GET, entity, Object.class);

        return resultMap.getBody();
    }

    private LatLng parseAndGetLatLng(Object body) {
        @SuppressWarnings("unchecked")
        Map<String, List<Map<String, Object>>> map
            = (Map<String, List<Map<String, Object>>>) body;

        List<Map<String, Object>> documents = map.get("documents");
        if (documents == null) {
            return null;
        }

        Map<String, Object> address = documents.get(0);

        if (address == null || address.size() == 0) {
            return null;
        }
        LatLng latLng = new LatLng();
        Object y = address.get("y");
        Object x = address.get("x");
        if (x == null || y == null) {
            return null;
        }

        try {
            latLng.setLat(Double.parseDouble(y.toString()));
            latLng.setLng(Double.parseDouble(x.toString()));
        } catch (NumberFormatException e) {
            return null;
        }

        return latLng;
    }
}
