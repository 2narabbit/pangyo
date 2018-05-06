package com.adinstar.pangyo.controller.api;

import com.adinstar.pangyo.common.annotation.MustLogin;
import com.adinstar.pangyo.constant.ViewModelName;
import com.adinstar.pangyo.model.*;
import com.adinstar.pangyo.service.StarService;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/star")
public class StarApiController {
    private static final int LIST_SIZE = 3;

    @Autowired
    private StarService starService;

    @ApiOperation("getStarList")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "rankId", value = "next rank Id", paramType = "query", dataType = "Long"),
            @ApiImplicitParam(name = "isJoined", value = "가입된 star 정보 포함", paramType = "query", dataType = "boolean")
    })
    @ApiResponses(value = {@ApiResponse(code = 200, message = "OK", response = FeedResponse.class)})
    @RequestMapping(method = RequestMethod.GET)
    public FeedResponse<RankData<Star>> getList(@RequestParam(value = "rankId", required = false) Long rankId,
                                                @RequestParam(value = "isJoined", defaultValue = "false") boolean isJoined,
                                                @ModelAttribute(ViewModelName.AUTH) LoginInfo loginInfo) {
        if (isJoined == false && loginInfo != null) {
            return starService.getNotJoinedStarRankListByUserId(loginInfo.getId(), Optional.ofNullable(rankId), LIST_SIZE);
        }
        return starService.getStarRankList(Optional.ofNullable(rankId), LIST_SIZE);
    }

    // 나중에 어드민 권한 관련해서 챙기도록+_+ (어드민 용 api 나오면 그곳으로 빼던지!!!)
    @ApiOperation("addStar")
    @ApiImplicitParams({
            @ApiImplicitParam(name="star", value="star object", paramType="body", required=true, dataType="Star")
    })
    @ApiResponses(value = {@ApiResponse(code = 200, message = "OK", response = FeedResponse.class)})
    @RequestMapping(method = RequestMethod.POST)
    public void add(@RequestBody Star star) {
        starService.add(star);
    }

    // 나중에 어드민 권한 관련해서 챙기도록+_+ (어드민 용 api 나오면 그곳으로 빼던지!!!)
    @ApiOperation("modifyStar")
    @ApiImplicitParams({
            @ApiImplicitParam(name="star", value="star object", paramType="body", required=true, dataType="Star")
    })
    @ApiResponses(value = {@ApiResponse(code = 200, message = "OK", response = FeedResponse.class)})
    @RequestMapping(method = RequestMethod.PUT)
    public void modify(@RequestBody Star star) {
        starService.modify(star);
    }

    @ApiOperation("getMyStarList")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "rankId", value = "next rank Id", paramType = "query", dataType = "Long")
    })
    @ApiResponses(value = {@ApiResponse(code = 200, message = "OK", response = FeedResponse.class)})
    @RequestMapping(value = "/my", method = RequestMethod.GET)
    @MustLogin
    public FeedResponse<RankData<Star>> getListByUserId(@RequestParam(value = "rankId", required = false) Long rankId,
                                                        @ModelAttribute(ViewModelName.AUTH) LoginInfo loginInfo) {
        return starService.getJoinedStarRankListByUserId(loginInfo.getId(), Optional.ofNullable(rankId), LIST_SIZE);
    }

    ////////////////////////////////////////////////////// 2depth //////////////////////////////////////////////////////
    @ApiOperation("joinedStar")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "starId", value = "star Id", paramType = "path", dataType = "long")
    })
    @ApiResponses(value = { @ApiResponse(code = 200, message = "OK", response = Map.class)})
    @RequestMapping(value = "/join/{starId}", method = RequestMethod.POST)
    @MustLogin
    public void joinedStar(@PathVariable("starId") long starId,
                           @ModelAttribute(ViewModelName.AUTH) LoginInfo loginInfo) {
        starService.joinedStar(starId, loginInfo.getId());
    }

    @ApiOperation("secededStar")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "starId", value = "star Id", paramType = "path", dataType = "long")
    })
    @ApiResponses(value = { @ApiResponse(code = 200, message = "OK", response = Map.class)})
    @RequestMapping(value = "/join/{starId}", method = RequestMethod.DELETE)
    @MustLogin
    public void secededStar(@PathVariable("starId") long starId,
                            @ModelAttribute(ViewModelName.AUTH) LoginInfo loginInfo) {
        starService.secededStar(starId, loginInfo.getId());
    }
}
