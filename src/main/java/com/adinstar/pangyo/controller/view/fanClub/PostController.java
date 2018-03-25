package com.adinstar.pangyo.controller.view.fanClub;


import com.adinstar.pangyo.service.PostService;
import com.adinstar.pangyo.service.StarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import static com.adinstar.pangyo.constant.ViewModelName.POST;
import static com.adinstar.pangyo.constant.ViewModelName.STAR;

@Controller
@RequestMapping("/fanClub/{starId}/post")
public class PostController {

    @Autowired
    private StarService starService;

    @Autowired
    private PostService postService;

    @RequestMapping(value = "/{postId}", method = RequestMethod.GET)
    public String get(@PathVariable("starId") long starId,
                      @PathVariable("postId") long postId,
                      Model model) {
        try {
            postService.increaseViewCount(starId, postId, 1);
        } catch (Exception e) {
            // ignore
        }

        model.addAttribute(POST, postService.getByStarIdAndId(starId, postId));

        return "fanClub/post/detail";
    }

    @RequestMapping(value = "/write", method = RequestMethod.GET)
    public String getWriteForm(@PathVariable("starId") long starId,
                                   @RequestParam(value = "postId", required = false) Long postId,
                                   Model model) {
        if (postId != null) {
            model.addAttribute(POST, postService.getByStarIdAndId(starId, postId));
        }
        model.addAttribute(STAR, starService.getById(starId));
        return "fanClub/post/form";
    }
}
