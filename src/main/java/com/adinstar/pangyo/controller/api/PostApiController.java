package com.adinstar.pangyo.controller.api;

import com.adinstar.pangyo.common.annotation.CheckAuthority;
import com.adinstar.pangyo.common.annotation.MustLogin;
import com.adinstar.pangyo.constant.ViewModelName;
import com.adinstar.pangyo.controller.exception.BadRequestException;
import com.adinstar.pangyo.controller.exception.UnauthorizedException;
import com.adinstar.pangyo.model.FeedResponse;
import com.adinstar.pangyo.model.Post;
import com.adinstar.pangyo.model.ViewerInfo;
import com.adinstar.pangyo.service.PostService;
import com.adinstar.pangyo.service.StarService;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/post")
@MustLogin
public class PostApiController {
    @Autowired
    private PostService postService;

    @Autowired
    private StarService starService;

    @ApiOperation("getPostListByStarId")
    @ApiImplicitParams({
            @ApiImplicitParam(name="starId", value="star id", paramType="query", dataType="Long"),
            @ApiImplicitParam(name="lastId", value="last post id", paramType="query", dataType="Long")
    })
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = FeedResponse.class)
    })
    @RequestMapping(method = RequestMethod.GET)
    @CheckAuthority
    public FeedResponse<Post> getListByStarId(@RequestParam("starId") long starId,
                                              @RequestParam(value = "lastId", required = false) Long lastId,
                                              @ModelAttribute(ViewModelName.VIEWER) ViewerInfo viewerInfo) {
        return postService.getListByStarId(starId, Optional.ofNullable(lastId));
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
    @CheckAuthority
    public Post get(@PathVariable("postId") long postId) {
        // fixme: CheckAuthority 에서도 get을 하기 때문에 2번 get을 하게됨. Mybatis 캐시로 해결하자
        // 아마 미자님도 2번 get하는 이슈때문에 여기서 auth 체크를 한 것 같은데 맞나요?^^;
        return postService.getById(postId);
    }

    @ApiOperation("addPost")
    @ApiImplicitParams({
            @ApiImplicitParam(name="post", value="post object", paramType="body", required=true, dataType="Post")
    })
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = Map.class)
    })
    @RequestMapping(method = RequestMethod.POST)
    public void add(@RequestBody Post post,
                    @ModelAttribute(ViewModelName.VIEWER) ViewerInfo viewerInfo) {
        if (post.getStar() == null) {   // TODO: RequestBody 도 pathInterceptor에서 처리해줘야하나ㅠㅠ
            throw BadRequestException.INVALID_PARAM;
        }

        if (!starService.isJoined(post.getStar().getId(), viewerInfo.getId())) {
            throw UnauthorizedException.NEED_JOIN;
        }

        post.setUser(viewerInfo.getUser());
        postService.add(post);
    }

    @ApiOperation("modifyPost")
    @ApiImplicitParams({
            @ApiImplicitParam(name="post", value="post object", paramType="body", required=true, dataType="Post")
    })
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = Map.class)
    })
    @RequestMapping(value = "/{postId}", method = RequestMethod.PUT)
    @CheckAuthority(isOwner = true)
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
    @CheckAuthority(isOwner = true)
    public void remove(@PathVariable("postId") long postId) {
        postService.remove(postId);
    }
}