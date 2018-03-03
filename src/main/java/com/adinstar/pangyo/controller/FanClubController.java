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
    public String getRecentList(@PathVariable("starId") long starId, Model model) {
        model.addAttribute("response", postService.findAllByStarId(starId, null));
        model.addAttribute("star", starService.findById(starId));
        return "fanClub/list";
    }

    @RequestMapping(value = "/post/{postId}", method = RequestMethod.GET)
    public String get(Model model, @PathVariable("postId") Integer postId) {
        postService.increaseViewCount(postId);

        model.addAttribute("post", postService.findById(postId));
        return "fanClub/post/detail";
    }

    @RequestMapping(value = "/write", method = RequestMethod.GET)
    public String getWriteForm(@PathVariable("starId") long starId,
                               @RequestParam(value = "postId", required = false) Long postId,
                               Model model) {
        if (postId != null) {
            model.addAttribute("post", postService.findById(postId));
        }
        model.addAttribute("star", starService.findById(starId));
        return "fanClub/post/form";
    }
}
