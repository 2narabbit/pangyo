package com.adinstar.pangyo.controller.api;


import com.adinstar.pangyo.common.annotation.MustLogin;
import com.adinstar.pangyo.constant.ViewModelName;
import com.adinstar.pangyo.model.FeedResponse;
import com.adinstar.pangyo.model.Post;
import com.adinstar.pangyo.model.PostFeedResponse;
import com.adinstar.pangyo.model.ViewerInfo;
import com.adinstar.pangyo.service.PostService;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Optional;


@RestController
@RequestMapping("/api/post")
public class PostApiController {
    @Autowired
    private PostService postService;

    @ApiOperation("getPostListByStarId")
    @ApiImplicitParams({
            @ApiImplicitParam(name="starId", value="star id", paramType="query", dataType="Long"),
            @ApiImplicitParam(name="lastId", value="last post id", paramType="query", dataType="Long")
    })
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = FeedResponse.class)
    })
    @RequestMapping(method = RequestMethod.GET)
    public PostFeedResponse getListByStarId(@RequestParam("starId") long starId,
                                            @RequestParam(value = "lastId", required = false) Long lastId,
                                            @ModelAttribute(ViewModelName.VIEWER) ViewerInfo viewerInfo) {
        return postService.getListByStarId(starId, Optional.ofNullable(lastId), viewerInfo == null ? null : viewerInfo.getId());
    }

    @ApiOperation("getPost")
    @ApiImplicitParams({
            @ApiImplicitParam(name="postId", value="post id", paramType="path", required=true, dataType="long")
    })
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = Post.class),
            @ApiResponse(code = 404, message = "Not Found")
    })
    @RequestMapping(value = "/{postId}", method = RequestMethod.GET)
    public Post get(@PathVariable("postId") long postId) {
        return postService.getById(postId);
    }

    //
    // test : curl -X POST -H "Content-Type: application/json" -d '{"star":{"id":2}, "user":{"id":2}, "body":"api test"}' http://localhost:8080/api/post
    //
    @ApiOperation("addPost")
    @ApiImplicitParams({
            @ApiImplicitParam(name="post", value="post object", paramType="body", required=true, dataType="Post")
    })
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = Map.class)
    })
    @RequestMapping(method = RequestMethod.POST)
    @MustLogin
    public void add(@RequestBody Post post) {
        postService.add(post);
    }

    //
    // test : curl -X PUT -H "Content-Type: application/json" -d '{"id":15, "body":"api modify test", "img":"https://t1.daumcdn.net/news/201802/21/seouleconomy/20180221112112176gmfz.jpg"}' http://localhost:8080/api/post
    //
    @ApiOperation("modifyPost")
    @ApiImplicitParams({
            @ApiImplicitParam(name="post", value="post object", paramType="body", required=true, dataType="Post")
    })
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = Map.class)
    })
    @RequestMapping(method = RequestMethod.PUT)
    @MustLogin
    public void modify(@RequestBody Post post) {
        postService.modify(post);
    }

    @ApiOperation("removePost")
    @ApiImplicitParams({
            @ApiImplicitParam(name="postId", value="post id", paramType="path", required=true, dataType="long")
    })
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = Map.class)
    })
    @RequestMapping(value = "/{postId}", method = RequestMethod.DELETE)
    @MustLogin
    public void remove(@PathVariable("postId") long postId) {
        postService.remove(postId);
    }
}
