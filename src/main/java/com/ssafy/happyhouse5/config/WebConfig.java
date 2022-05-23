package com.ssafy.happyhouse5.config;

import java.util.Locale;
import java.util.TimeZone;
import javax.annotation.PostConstruct;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry
            .addMapping("/**")
            .allowedOrigins("http://127.0.0.1:3000/", "http://localhost:3000/",
                "https://vinil-house-client-clone.vercel.app/")
            .allowedMethods("GET", "POST", "PATCH", "DELETE")
            .allowCredentials(true)
            .exposedHeaders("Set-Cookie");
    }

    @PostConstruct
    public void timeZoneAndLocaleSet() {
        TimeZone.setDefault(TimeZone.getTimeZone("Asia/Seoul"));
        Locale.setDefault(Locale.KOREA);
    }
}
