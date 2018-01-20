package com.adinstar.pangyo.controller.api;

import com.adinstar.pangyo.model.Test;
import com.adinstar.pangyo.service.TestService;
import io.swagger.annotations.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api/test")
public class TestApiContoller {
    @Autowired
    private TestService testService;

    static final Logger log = LoggerFactory.getLogger(TestApiContoller.class);

    @RequestMapping(value = "/string", method = RequestMethod.GET)
    public String string() {
        return "Greetings from Spring Boot!";
    }

    @RequestMapping(value = "/object", method = RequestMethod.GET)
    public Test object() {
        Test test = new Test();
        test.setVal1(1);
        test.setVal2("ellie");
        test.setVal3(true);

        return test;
    }

    @ApiOperation("getModel")
    @ApiImplicitParams({
            @ApiImplicitParam(name="val1", value="value one", paramType="query", dataType="Integer")
    })
    @ApiResponses(value={
            @ApiResponse(code=200, message="Success ^-^", response=Test.class),
            @ApiResponse(code=404, message="Not Found T.T")
    })
    @RequestMapping(value = "/model", method = RequestMethod.GET)
    public Test model(@RequestParam Integer val1) {
        log.debug("GET model (param:{})", val1);
        return testService.get(val1);
    }
}
