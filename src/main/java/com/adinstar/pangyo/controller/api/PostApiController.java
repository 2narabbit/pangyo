package com.adinstar.pangyo.controller.api;


import com.adinstar.pangyo.helper.PageHelper;
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
            @ApiImplicitParam(name="starId", value="star id", paramType="query", dataType="Integer"),
            @ApiImplicitParam(name="page", value="page number", paramType="query", dataType="Integer"),
            @ApiImplicitParam(name="size", value="page size", paramType="query", dataType="Integer")
    })
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = List.class)
    })
    @RequestMapping(value = "", method = RequestMethod.GET)
    public List<Post> getPostListByStarId(@RequestParam(value = "starId", required = false) Integer starId,
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
    public Post getPost(@PathVariable("postId") Integer postId) {
        return postService.findById(postId);
    }
}