package com.adinstar.pangyo.controller.api;


import com.adinstar.pangyo.model.Post;
import com.adinstar.pangyo.service.PostService;
import io.swagger.annotations.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/post")
public class PostApiController {
    private static final Logger logger = LoggerFactory.getLogger(PostApiController.class);

    @Autowired
    private PostService postService;

    @ApiOperation("getModel")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "val1", value = "value one", paramType = "query", dataType = "Integer")
    })
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success ^-^", response = Post.class),
            @ApiResponse(code = 404, message = "Not Found T.T")
    })
    @RequestMapping(value = "/model", method = RequestMethod.GET)
    public Post model(@RequestParam Integer val1) {
        logger.debug("GET model (param:{})", val1);
        return postService.findById(val1);
    }
}