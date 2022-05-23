package com.ssafy.happyhouse5.controller.pagecontroller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping(value = {"/", "/index"})
    public String redirectToSwagger() {
        return "redirect:/swagger-ui/";
    }
}
