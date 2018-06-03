package com.adinstar.pangyo.controller.api;

import com.adinstar.pangyo.common.annotation.MustLogin;
import com.adinstar.pangyo.constant.ViewModelName;
import com.adinstar.pangyo.model.*;
import com.adinstar.pangyo.service.StarService;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
                                                @ModelAttribute(ViewModelName.VIEWER) ViewerInfo viewerInfo) {
        if (isJoined == false && viewerInfo != null) {
            return starService.getNotJoinedStarRankListByUserId(viewerInfo.getId(), Optional.ofNullable(rankId), LIST_SIZE);
        }
        return starService.getStarRankList(Optional.ofNullable(rankId), LIST_SIZE);
    }

    @ApiOperation("getMyStarList")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "rankId", value = "next rank Id", paramType = "query", dataType = "Long")
    })
    @ApiResponses(value = {@ApiResponse(code = 200, message = "OK", response = FeedResponse.class)})
    @RequestMapping(value = "/my", method = RequestMethod.GET)
    @MustLogin
    public FeedResponse<RankData<Star>> getListByUserId(@RequestParam(value = "rankId", required = false) Long rankId,
                                                        @ModelAttribute(ViewModelName.VIEWER) ViewerInfo viewerInfo) {
        return starService.getJoinedStarRankListByUserId(viewerInfo.getId(), Optional.ofNullable(rankId), LIST_SIZE);
    }
}
