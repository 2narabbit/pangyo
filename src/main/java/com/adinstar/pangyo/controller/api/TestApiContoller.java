package com.adinstar.pangyo.controller.api;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/test")
public class TestApiContoller {
    @RequestMapping("/index")
    public String index() {
        return "Greetings from Spring Boot!";
    }
}
