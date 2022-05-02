package com.ssafy.happyhouse5.controller.restcontroller;

import com.ssafy.happyhouse5.service.impl.BoardSearchOption;
import java.util.HashMap;
import java.util.Map;
import javax.annotation.PostConstruct;
import org.springframework.stereotype.Component;

@Component
public class OptionMapper {

    private final Map<String, BoardSearchOption> mapper = new HashMap<>();

    @PostConstruct
    private void postConstruct(){
        mapper.put("title", BoardSearchOption.LIKE_TITLE);
        mapper.put("content", BoardSearchOption.LIKE_CONTENT);
        mapper.put("member", BoardSearchOption.BY_MEMBER_ID);
    }

    public BoardSearchOption get(String key){
        return mapper.get(key);
    }
}
