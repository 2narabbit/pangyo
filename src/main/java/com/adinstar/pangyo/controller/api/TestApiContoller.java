package com.adinstar.pangyo.controller.api;

import com.adinstar.pangyo.model.Test;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/test")
public class TestApiContoller {
    @RequestMapping("/string")
    public String string() {
        return "Greetings from Spring Boot!";
    }

    @RequestMapping("/object")
    public Test object() {
        Test test = new Test();
        test.setVal1(1);
        test.setVal2("ellie");
        test.setVal3(true);

        return test;
    }
}
