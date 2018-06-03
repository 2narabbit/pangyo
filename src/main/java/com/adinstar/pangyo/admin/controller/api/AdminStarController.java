package com.adinstar.pangyo.admin.controller.api;

import com.adinstar.pangyo.model.FeedResponse;
import com.adinstar.pangyo.model.Star;
import com.adinstar.pangyo.service.StarService;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/admin/star")
public class AdminStarController {

    @Autowired
    private StarService starService;

    @ApiOperation("addStar")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "star", value = "star object", paramType = "body", required = true, dataType = "Star")
    })
    @ApiResponses(value = {@ApiResponse(code = 200, message = "OK", response = FeedResponse.class)})
    @RequestMapping(method = RequestMethod.POST)
    public void add(@RequestBody Star star) {
        starService.add(star);
    }

    @ApiOperation("modifyStar")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "star", value = "star object", paramType = "body", required = true, dataType = "Star")
    })
    @ApiResponses(value = {@ApiResponse(code = 200, message = "OK", response = FeedResponse.class)})
    @RequestMapping(value = "/{starId}", method = RequestMethod.PUT)
    public void modify(@RequestBody Star star) {
        starService.modify(star);
    }

}
