package com.adinstar.pangyo.controller;


import com.adinstar.pangyo.model.Post;
import com.adinstar.pangyo.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/post")
public class PostController {

    @Autowired
    private PostService postService;

    @RequestMapping(value = {"", "/", "top", "home"}, method = RequestMethod.GET)
    public String getRecentList(Model model) {
        model.addAttribute("response", postService.findAll(null));
        return "post/list";
    }

    @RequestMapping(value = "/{postId}", method = RequestMethod.GET)
    public String get(Model model, @PathVariable("postId") Integer postId) {
        postService.increaseViewCount(postId);
        model.addAttribute("post", postService.findById(postId));
        return "post/detail";
    }

    @RequestMapping(value = "/write", method = RequestMethod.GET)
    public String getWriteForm(@RequestParam(value = "id", required = false) Integer id, Model model) {
        if (id != null) {
            model.addAttribute("post", postService.findById(id));
        }
        return "post/form";
    }
}
