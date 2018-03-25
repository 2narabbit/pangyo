package com.adinstar.pangyo.controller;


import com.adinstar.pangyo.service.PostService;
import com.adinstar.pangyo.service.StarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/fanClub/{starId}")
public class FanClubController {

    @Autowired
    private PostService postService;

    @Autowired
    private StarService starService;

    @RequestMapping(value = {"", "/", "top", "home"}, method = RequestMethod.GET)
    public String getTopFeed(@PathVariable("starId") long starId, Model model) {
        model.addAttribute("response", postService.getAllByStarId(starId, null));
        model.addAttribute("star", starService.getById(starId));
        return "fanClub/list";
    }

    @RequestMapping(value = "/post/{postId}", method = RequestMethod.GET)
    public String getPostDetail(@PathVariable("starId") long starId,
                                @PathVariable("postId") long postId,
                                Model model) {
        postService.increaseViewCount(postId);

        model.addAttribute("post", postService.getById(postId));
        model.addAttribute("starId", starId);
        return "fanClub/post/detail";
    }

    // TODO : uri 가 적절하지 못한것 같다,, (/post/write로 하는게 맞을것 같기도 하고,,)
    @RequestMapping(value = "/write", method = RequestMethod.GET)
    public String getPostWriteForm(@PathVariable("starId") long starId,
                                   @RequestParam(value = "postId", required = false) Long postId,
                                   Model model) {
        if (postId != null) {
            model.addAttribute("post", postService.getById(postId));
        }
        model.addAttribute("star", starService.getById(starId));
        return "fanClub/post/form";
    }
}
