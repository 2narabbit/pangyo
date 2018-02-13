package com.adinstar.pangyo.controller;


import com.adinstar.pangyo.helper.PageHelper;
import com.adinstar.pangyo.model.Post;
import com.adinstar.pangyo.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/post")
public class PostController {

    @Autowired
    private PostService postService;

    @RequestMapping(value = {"", "/", "top", "home"}, method = RequestMethod.GET)
    public String getRecentList(Model model) {
        model.addAttribute("list", postService.findAll(new PageHelper(1, 20)));
        return "post/list";
    }

    @RequestMapping(value = "/edit", method = RequestMethod.GET)
    public String getTemplate(@RequestParam(value = "id", required = false) Integer id, Model model) {
        if (id != null) {
            model.addAttribute("post", postService.findById(id));
        }
        return "post/template";
    }

    // 이 녀석들이 여기 있는게 좋을까? api로 호출하는게...
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public String save(@ModelAttribute("Post") Post post) {
        return "redirect:/post/edit?id=" + postService.save(post);
    }

    // 이 녀석들이 여기 있는게 좋을까? api로 호출하는게...
    @RequestMapping(value = "/change", method = RequestMethod.PUT)
    public String modify(@ModelAttribute("Post") Post post) {
        postService.modify(post);
        return "redirect:/post/edit?id=" + post.getId();
    }
}