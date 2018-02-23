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
        model.addAttribute("list", postService.findAll(null));
        return "post/list";
    }

    @RequestMapping(value = "/{postId}", method = RequestMethod.GET)
    public String get(Model model, @PathVariable("postId") Integer postId) {
        model.addAttribute("post", postService.findById(postId));
        return "post/detail";
    }

    @RequestMapping(value = "/edit", method = RequestMethod.GET)
    public String getTemplate(@RequestParam(value = "id", required = false) Integer id, Model model) {
        // TODO: 기획 나오면 add/edit method 분리 구현
        if (id != null) {
            model.addAttribute("post", postService.findById(id));
        }
        return "post/template";
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public String save(@ModelAttribute("Post") Post post) {
        return "redirect:/post/edit?id=" + postService.save(post);
    }

    @RequestMapping(value = "/change", method = RequestMethod.PUT)
    public String modify(@ModelAttribute("Post") Post post) {
        postService.modify(post);
        return "redirect:/post/edit?id=" + post.getId();
    }
}
