package com.adinstar.pangyo.controller.view.fanClub;


import com.adinstar.pangyo.constant.PangyoEnum;
import com.adinstar.pangyo.constant.ViewModelName;
import com.adinstar.pangyo.model.ViewerInfo;
import com.adinstar.pangyo.service.CommentService;
import com.adinstar.pangyo.service.LikeService;
import com.adinstar.pangyo.service.PostService;
import com.adinstar.pangyo.service.StarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

import static com.adinstar.pangyo.constant.ViewModelName.*;

@Controller
@RequestMapping("/fanClub/{starId}/post")
public class PostController {

    @Autowired
    private StarService starService;

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
        try {
            postService.updateViewCount(postId, 1);
        } catch (Exception e) {
            // ignore
        }

        model.addAttribute(POST, postService.getById(postId));
        model.addAttribute(COMMENT_FEED, commentService.getList(PangyoEnum.ContentType.POST, postId, Optional.empty(), viewerInfo == null ? null : viewerInfo.getId()));
        model.addAttribute(IS_LIKED, likeService.isActioned(PangyoEnum.ContentType.POST, postId, viewerInfo.getId()));

        return "fanClub/post/detail";
    }

    @RequestMapping(value = "/write", method = RequestMethod.GET)
    public String getWriteForm(@PathVariable("starId") long starId,
                               @RequestParam(value = "postId", required = false) Long postId,
                               Model model) {
        if (postId != null) {
            model.addAttribute(POST, postService.getById(postId));
        }
        model.addAttribute(STAR, starService.getById(starId));
        return "fanClub/post/form";
    }
}
