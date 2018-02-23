package com.adinstar.pangyo.controller.api;


import com.adinstar.pangyo.model.Post;
import com.adinstar.pangyo.service.PostService;
import io.swagger.annotations.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


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
    public List<Post> getListByStarId(@RequestParam(value = "starId", required = false) Long starId,
                                      @RequestParam(value = "lastId", required = false) Long lastId) {
        if (starId == null) {
            return postService.findAll(lastId);
        } else {
            return postService.findAllByStarId(starId, lastId);
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
        return postService.findById(postId);
    }
}
