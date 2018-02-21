package com.adinstar.pangyo.controller.api;


import com.adinstar.pangyo.helper.PageHelper;
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
            @ApiImplicitParam(name="starId", value="star id", paramType="query", dataType="Integer"),
            @ApiImplicitParam(name="page", value="page number", paramType="query", dataType="Integer"),
            @ApiImplicitParam(name="size", value="page size", paramType="query", dataType="Integer")
    })
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = List.class)
    })
    @RequestMapping(method = RequestMethod.GET)
    public List<Post> getListByStarId(@RequestParam(value = "starId", required = false) Integer starId,
                                          @RequestParam(value = "page", defaultValue = "1") Integer page,
                                          @RequestParam(value = "size", defaultValue = "20") Integer size) {
        if (starId == null) {
            return postService.findAll(new PageHelper(page, size));
        } else {
            return postService.findAllByStarId(starId, new PageHelper(page, size));
        }
    }

    @ApiOperation("getPost")
    @ApiImplicitParams({
            @ApiImplicitParam(name="postId", value="post id", paramType="path", required=true, dataType="Integer")
    })
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = Post.class),
            @ApiResponse(code = 404, message = "Not Found")
    })
    @RequestMapping(value = "/{postId}", method = RequestMethod.GET)
    public Post get(@PathVariable("postId") Integer postId) {
        return postService.findById(postId);
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
    public Map<Object, Object> add(@RequestBody Post post) {
        long postId = postService.save(post);

        // FIXME: API 응답 객체 있으면 좋을 것 같은데.. 어떻게 만들어야 예쁠지?
        return ImmutableMap.builder().put("id", postId).build();
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
    public Map<Object, Object> modify(@RequestBody Post post) {
        int rv = postService.modify(post);

        // FIXME: API 응답 객체 있으면 좋을 것 같은데.. 어떻게 만들어야 예쁠지?
        return ImmutableMap.builder().put("rv", rv).build();
    }
}