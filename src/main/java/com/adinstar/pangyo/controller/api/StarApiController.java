package com.adinstar.pangyo.controller.api;

import com.adinstar.pangyo.common.annotation.MustLogin;
import com.adinstar.pangyo.constant.ViewModelName;
import com.adinstar.pangyo.model.*;
import com.adinstar.pangyo.service.StarService;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

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
            @ApiImplicitParam(name = "isJoined", value = "가입된 star 정보 포함", paramType = "query", defaultValue = "false", dataType = "boolean")
    })
    @GetMapping
    public FeedResponse<RankData<Star>> getList(@RequestParam(value = "rankId", required = false) Long rankId,
                                                @RequestParam(value = "isJoined", defaultValue = "false") boolean isJoined,
                                                @ApiIgnore @ModelAttribute(ViewModelName.VIEWER) ViewerInfo viewerInfo) {
        if (isJoined == false && viewerInfo != null) {
            return starService.getNotJoinedStarRankListByUserId(viewerInfo.getId(), Optional.ofNullable(rankId), LIST_SIZE);
        }
        return starService.getStarRankList(Optional.ofNullable(rankId), LIST_SIZE);
    }

    @ApiOperation("getMyStarList")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "rankId", value = "next rank Id", paramType = "query", dataType = "Long")
    })
    @GetMapping(value = "/me")
    @MustLogin
    public FeedResponse<RankData<Star>> getListByUserId(@RequestParam(value = "rankId", required = false) Long rankId,
                                                        @ApiIgnore @ModelAttribute(ViewModelName.VIEWER) ViewerInfo viewerInfo) {
        return starService.getJoinedStarRankListByUserId(viewerInfo.getId(), Optional.ofNullable(rankId), LIST_SIZE);
    }

    @ApiOperation("joinedStar")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "starId", value = "star Id", paramType = "path", dataType = "long")
    })
    @PostMapping(value = "/join/{starId}")
    @MustLogin
    public void joinedStar(@PathVariable("starId") long starId,
                           @ApiIgnore @ModelAttribute(ViewModelName.VIEWER) ViewerInfo viewerInfo) {
        starService.joinedStar(starId, viewerInfo.getId());
    }

    @ApiOperation("secededStar")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "starId", value = "star Id", paramType = "path", dataType = "long")
    })
    @DeleteMapping(value = "/join/{starId}")
    @MustLogin
    public void secededStar(@PathVariable("starId") long starId,
                            @ApiIgnore @ModelAttribute(ViewModelName.VIEWER) ViewerInfo viewerInfo) {
        starService.secededStar(starId, viewerInfo.getId());
    }
}
