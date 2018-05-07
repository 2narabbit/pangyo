package com.adinstar.pangyo.controller.api;

import com.adinstar.pangyo.common.annotation.MustLogin;
import com.adinstar.pangyo.constant.PangyoEnum;
import com.adinstar.pangyo.constant.ViewModelName;
import com.adinstar.pangyo.model.FeedResponse;
import com.adinstar.pangyo.model.LoginInfo;
import com.adinstar.pangyo.service.PollService;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/poll")
public class PollApiController {

    @Autowired
    private PollService pollService;

    @ApiOperation("addPoll")
    @ApiImplicitParams({
            @ApiImplicitParam(name="contentType", value="content type", paramType="path", dataType="String"),
            @ApiImplicitParam(name="contentId", value="content id", paramType="path", dataType="Long"),
    })
    @ApiResponses(value = {@ApiResponse(code = 200, message = "OK", response = FeedResponse.class)})
    @RequestMapping(value = "/{contentType}/{contentId}", method = RequestMethod.POST)
    @MustLogin
    public void add(@PathVariable("contentType") String contentType,
                    @PathVariable("contentId") long contentId,
                    @ModelAttribute(ViewModelName.AUTH) LoginInfo loginInfo) {
        pollService.add(PangyoEnum.ContentType.valueOf(contentType), contentId, loginInfo.getId());
    }

    @ApiOperation("removePoll")
    @ApiImplicitParams({
            @ApiImplicitParam(name="contentType", value="content type", paramType="path", dataType="String"),
            @ApiImplicitParam(name="contentId", value="content id", paramType="path", dataType="Long"),
    })
    @ApiResponses(value = {@ApiResponse(code = 200, message = "OK", response = FeedResponse.class)})
    @RequestMapping(value = "/{contentType}/{contentId}", method = RequestMethod.DELETE)
    @MustLogin
    public void remove(@PathVariable("contentType") String contentType,
                    @PathVariable("contentId") long contentId,
                    @ModelAttribute(ViewModelName.AUTH) LoginInfo loginInfo) {
        pollService.remove(PangyoEnum.ContentType.valueOf(contentType), contentId, loginInfo.getId());
    }
}