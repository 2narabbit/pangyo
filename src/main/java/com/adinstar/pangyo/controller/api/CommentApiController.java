package com.adinstar.pangyo.controller.api;

import com.adinstar.pangyo.common.annotation.CheckAuthority;
import com.adinstar.pangyo.common.annotation.MustLogin;
import com.adinstar.pangyo.constant.PangyoEnum;
import com.adinstar.pangyo.constant.ViewModelName;
import com.adinstar.pangyo.controller.exception.BadRequestException;
import com.adinstar.pangyo.controller.exception.UnauthorizedException;
import com.adinstar.pangyo.model.Comment;
import com.adinstar.pangyo.model.FeedResponse;
import com.adinstar.pangyo.model.ViewerInfo;
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

    @ApiOperation("addComment")
    @ApiImplicitParams({
            @ApiImplicitParam(name="comment", value="comment object", paramType="body", required=true, dataType="Comment")
    })
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK")
    })
    @RequestMapping(method = RequestMethod.POST)
    @MustLogin
    public void add(@RequestBody Comment comment,
                    @ModelAttribute(ViewModelName.VIEWER) ViewerInfo viewerInfo) {
        // checked!! >  Post의 comment 작성도 join 여부 check 해야하지 않을까?

        comment.setUser(viewerInfo.getUser());
        commentService.add(comment);
    }

    @ApiOperation("modifyComment")
    @ApiImplicitParams({
            @ApiImplicitParam(name="comment", value="comment object", paramType="body", required=true, dataType="Comment")
    })
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK")
    })
    @RequestMapping(value = "/{commentId}", method = RequestMethod.PUT)
    @MustLogin
    @CheckAuthority(isFan = false, isOwner = true)
    public void modify(@RequestBody Comment comment) {
        commentService.modify(comment);
    }

    @ApiOperation("removeComment")
    @ApiImplicitParams({
            @ApiImplicitParam(name="commentId", value="comment id", paramType="path", required=true, dataType="long")
    })
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK")
    })
    @RequestMapping(value = "/{commentId}", method = RequestMethod.DELETE)
    @MustLogin
    @CheckAuthority(isFan = false, isOwner = true)
    public void remove(@PathVariable("commentId") long id) {
        commentService.remove(id);
    }
}
