package com.adinstar.pangyo.admin.controller;

import com.adinstar.pangyo.admin.service.RuleMaker;
import com.adinstar.pangyo.model.ExecutionRule;
import com.adinstar.pangyo.service.ExecutionRuleService;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/admin/schedule")
public class ScheduleController {

    @Autowired
    private ExecutionRuleService executionRuleService;

    @Autowired
    private RuleMaker ruleMaker;

    @ApiOperation("요청 회차의 ExecutionRule 정보 확인하기")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "turnNum", value = "요청 회차", paramType = "query", required = true, dataType = "long")
    })
    @ApiResponses(value = {@ApiResponse(code = 200, message = "OK", response = Map.class)})
    @RequestMapping(method = RequestMethod.GET)
    public List<ExecutionRule> get(@RequestParam long turnNum) {
        return executionRuleService.getExecutionRuleListByTurnNum(turnNum);
    }

    @ApiOperation("요청 회차의 ExecutionRule 추가하기")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "turnNum", value = "요청 회차", paramType = "query", required = true, dataType = "long")
    })
    @ApiResponses(value = {@ApiResponse(code = 200, message = "OK", response = Map.class)})
    @RequestMapping(method = RequestMethod.GET)
    public List<ExecutionRule> add(@RequestParam long turnNum) {
        ruleMaker.registeredExecutionRule(turnNum);
        return executionRuleService.getExecutionRuleListByTurnNum(turnNum);
    }
}