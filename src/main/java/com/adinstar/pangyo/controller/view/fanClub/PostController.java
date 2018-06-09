package com.adinstar.pangyo.controller.view.fanClub;


import com.adinstar.pangyo.common.annotation.CheckAuthority;
import com.adinstar.pangyo.common.annotation.MustLogin;
import com.adinstar.pangyo.constant.PangyoEnum;
import com.adinstar.pangyo.constant.ViewModelName;
import com.adinstar.pangyo.model.ViewerInfo;
import com.adinstar.pangyo.service.CommentService;
import com.adinstar.pangyo.service.LikeService;
import com.adinstar.pangyo.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

import static com.adinstar.pangyo.constant.ViewModelName.*;

@Controller
@RequestMapping("/fanClub/{starId}/post")
@MustLogin
@CheckAuthority
public class PostController {

    @Autowired
    private PostService postService;

    @Autowired
    private CommentService commentService;

    @Autowired
    private LikeService likeService;

    @RequestMapping(value = "/{postId}", method = RequestMethod.GET)
    public String get(@PathVariable("postId") long postId,
                      @ModelAttribute(ViewModelName.VIEWER) ViewerInfo viewerInfo,
                      Model model) {
        model.addAttribute(COMMENT_FEED, commentService.getList(PangyoEnum.ContentType.POST, postId, Optional.empty(), viewerInfo == null ? null : viewerInfo.getId()));
        model.addAttribute(IS_LIKED, viewerInfo != null && likeService.isActioned(PangyoEnum.ContentType.POST, postId, viewerInfo.getId()));
        postService.updateViewCount(postId, 1);

        return "fanClub/post/detail";
    }

    @RequestMapping(value = "/write", method = RequestMethod.GET)
    public String getWriteForm() {
        return "fanClub/post/form";
    }

    @RequestMapping(value = "/write/{postId}", method = RequestMethod.GET)
    @CheckAuthority(isOwner = true)
    public String getEditForm() {
        return "fanClub/post/form";
    }
}
