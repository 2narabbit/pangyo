package com.adinstar.pangyo.controller.api;


import com.adinstar.pangyo.model.FeedResponse;
import com.adinstar.pangyo.model.Post;
import com.adinstar.pangyo.service.PostService;
import com.google.common.collect.ImmutableMap;
import io.swagger.annotations.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("/api/post")
public class PostApiController {
    private static final Logger logger = LoggerFactory.getLogger(PostApiController.class);

    @Autowired
    private PostService postService;

    @ApiOperation("getPostListByStarId")
    @ApiImplicitParams({
            @ApiImplicitParam(name="starId", value="star id", paramType="query", dataType="Long"),
            @ApiImplicitParam(name="lastId", value="last post id", paramType="query", dataType="Long")
    })
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = List.class)
    })
    @RequestMapping(method = RequestMethod.GET)
    public FeedResponse<Post> getListByStarId(@RequestParam(value = "starId", required = false) Long starId,
                                        @RequestParam(value = "lastId", required = false) Long lastId) {
        if (starId == null) {
            return postService.getAll(lastId);
        } else {
            return postService.getAllByStarId(starId, lastId);
        }
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
    // test : curl -X POST -H "Content-Type: application/json" -d '{"starId":2, "user":{"id":2}, "body":"api test"}' http://localhost:8080/api/post
    //
    @ApiOperation("addPost")
    @ApiImplicitParams({
            @ApiImplicitParam(name="post", value="post object", paramType="body", required=true, dataType="Post")
    })
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = Map.class)
    })
    @RequestMapping(method = RequestMethod.POST)
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
    public void remove(@PathVariable("postId") long postId) {
        postService.remove(postId);
    }
}
