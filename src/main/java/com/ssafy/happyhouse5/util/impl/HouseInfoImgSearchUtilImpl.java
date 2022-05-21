package com.ssafy.happyhouse5.util.impl;

import com.ssafy.happyhouse5.util.HouseInfoImgSearchUtil;
import java.util.List;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
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
@Slf4j
public class HouseInfoImgSearchUtilImpl implements HouseInfoImgSearchUtil {

    @Value("${url.apt.search}")
    private String SEARCH_URL;

    @Value("${url.apt.image}")
    private String IMAGE_URL;

    @Override
    public String getImgLink(String aptName) {
        Object searchBody = requestAndGetBody(SEARCH_URL + aptName);
        String id = parseAndGetAptId(searchBody);
        if (id == null) return null; // or throw exception
        log.debug("getImgLink AptId(EXTERNAL API): {}", id);
        Object imageBody = requestAndGetBody(IMAGE_URL + id);
        return parseAndGetImgLink(imageBody);
    }

    private String parseAndGetImgLink(Object body) {
        @SuppressWarnings("unchecked")
        Map<String, String> map = (Map<String, String>) body;

        System.out.println(map);
        return map.get("image");
    }

    private Object requestAndGetBody(String url) {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders header = new HttpHeaders();
        HttpEntity<?> entity = new HttpEntity<>(header);

        UriComponents uri = UriComponentsBuilder.fromHttpUrl(url).build();
        ResponseEntity<?> resultMap = restTemplate.exchange(
            uri.toString(), HttpMethod.GET, entity, Object.class);

        return resultMap.getBody();
    }

    private String parseAndGetAptId(Object body) {
        @SuppressWarnings("unchecked")
        Map<String, List<Map<String, Object>>> map
            = (Map<String, List<Map<String, Object>>>) body;

        List<Map<String, Object>> items = map.get("items");
        if (items == null || items.size() == 0) return null;
        return items.get(0).get("id").toString();
    }
}
