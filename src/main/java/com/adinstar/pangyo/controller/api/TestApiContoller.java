package com.adinstar.pangyo.controller.api;

import com.adinstar.pangyo.model.Test;
import com.adinstar.pangyo.service.TestService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api/test")
public class TestApiContoller {
    @Autowired
    private TestService testService;

    static final Logger log = LoggerFactory.getLogger(TestApiContoller.class);

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

    @RequestMapping("/model")
    public Test model(@RequestParam Integer val1) {
        log.debug("GET model (param:{})", val1);
        return testService.get(val1);
    }
}
