package com.adinstar.pangyo.controller.api;

import com.adinstar.pangyo.common.annotation.MustLogin;
import com.adinstar.pangyo.constant.PangyoEnum;
import com.adinstar.pangyo.constant.ViewModelName;
import com.adinstar.pangyo.model.ViewerInfo;
import com.adinstar.pangyo.service.LikeService;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/like")
@MustLogin
public class LikeApiController {

    @Autowired
    private LikeService likeService;

    @ApiOperation("addLike")
    @ApiImplicitParams({
            @ApiImplicitParam(name="contentType", value="content type", paramType="path", dataType="String"),
            @ApiImplicitParam(name="contentId", value="content id", paramType="path", dataType="Long"),
    })
    @ApiResponses(value = {@ApiResponse(code = 200, message = "OK")})
    @RequestMapping(value = "/{contentType}/{contentId}", method = RequestMethod.POST)
    public void add(@PathVariable("contentType") String contentType,
                    @PathVariable("contentId") long contentId,
                    @ModelAttribute(ViewModelName.VIEWER) ViewerInfo viewerInfo) {
        likeService.add(PangyoEnum.ContentType.valueOf(contentType), contentId, viewerInfo.getId());
    }

    @ApiOperation("removeLike")
    @ApiImplicitParams({
            @ApiImplicitParam(name="contentType", value="content type", paramType="path", dataType="String"),
            @ApiImplicitParam(name="contentId", value="content id", paramType="path", dataType="Long"),
    })
    @ApiResponses(value = {@ApiResponse(code = 200, message = "OK")})
    @RequestMapping(value = "/{contentType}/{contentId}", method = RequestMethod.DELETE)
    public void remove(@PathVariable("contentType") String contentType,
                       @PathVariable("contentId") long contentId,
                       @ModelAttribute(ViewModelName.VIEWER) ViewerInfo viewerInfo) {
        likeService.remove(PangyoEnum.ContentType.valueOf(contentType), contentId, viewerInfo.getId());
    }
}