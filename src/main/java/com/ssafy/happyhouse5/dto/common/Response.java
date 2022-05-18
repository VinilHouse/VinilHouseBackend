package com.ssafy.happyhouse5.dto.common;

import static com.ssafy.happyhouse5.dto.common.ResponseStatus.*;

import lombok.Getter;
import lombok.Setter;
import org.springframework.lang.Nullable;

@Getter
@Setter
public class Response {

    ResponseStatus status;
    Object content;

    private Response(ResponseStatus status) {
        this.status = status;
    }

    public static Response success(@Nullable Object content) {
        Response response = success();
        response.content = content;
        return response;
    }

    public static Response success() {
        return new Response(SUCCESS);
    }

    public static Response fail(@Nullable Object content){
        Response response = fail();
        response.content = content;
        return response;
    }

    public static Response fail(){
        return new Response(FAIL);
    }
}
