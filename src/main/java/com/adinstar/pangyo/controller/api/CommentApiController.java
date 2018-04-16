package com.adinstar.pangyo.controller.api;

import com.adinstar.pangyo.constant.PangyoEnum;
import com.adinstar.pangyo.model.Comment;
import com.adinstar.pangyo.model.FeedResponse;
import com.adinstar.pangyo.service.CommentService;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/comment")
public class CommentApiController {
    @Autowired
    private CommentService commentService;

    @ApiOperation("getCommentList")
    @ApiImplicitParams({
            @ApiImplicitParam(name="contentType", value="content type", paramType="path", dataType="String"),
            @ApiImplicitParam(name="contentId", value="content id", paramType="path", dataType="Long"),
            @ApiImplicitParam(name="lastId", value="last post id", paramType="query", dataType="Long")
    })
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = FeedResponse.class)
    })
    @RequestMapping(value = "/{contentType}/{contentId}", method = RequestMethod.GET)
    public FeedResponse<Comment> getList(@PathVariable("contentType") String contentType,
                                         @PathVariable("contentId") long contentId,
                                         @RequestParam(value = "lastId", required = false) Long lastId) {
        return commentService.getList(PangyoEnum.ContentType.valueOf(contentType), contentId, Optional.ofNullable(lastId));
    }
}
