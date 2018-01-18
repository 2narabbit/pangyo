package com.adinstar.pangyo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/test")
public class TestController {
    @RequestMapping("/index")
    public String index(Model model) {
        model.addAttribute("name", "betty");
        return "test/index";
    }
}
