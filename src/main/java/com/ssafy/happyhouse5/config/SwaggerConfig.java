package com.ssafy.happyhouse5.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@EnableSwagger2
@Configuration
public class SwaggerConfig {
    @Bean
    public Docket api() {
        final ApiInfo apiInfo = new ApiInfoBuilder()
            .title("SSAFY HOUSE 5 - SPRING")
            .description("<h3>REST API 정보 제공</h3>")
            .contact(new Contact("gitlab", "https://lab.ssafy.com/re12io/happyhouse5", ""))
            .license("MIT License")
            .version("1.0")
            .build();

        return new Docket(DocumentationType.SWAGGER_2)
            .apiInfo(apiInfo)
            .select()
            .apis(RequestHandlerSelectors.basePackage("com.ssafy.happyhouse5.controller.restcontroller"))
            .paths(PathSelectors.ant("/**"))
            .build();
    }
}
