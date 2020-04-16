package com.adinstar.pangyo.controller;

import com.adinstar.pangyo.constant.ViewModelName;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/error")
public class ErrorController {

    @RequestMapping(value = {"/alert"}, method = RequestMethod.GET)
    public String sendMessage(@RequestParam(value = "message", defaultValue = "서비스가 원활하지 않습니다.") String message,
                              Model model) {
        model.addAttribute(ViewModelName.ERROR_MESSAGE, message);
        return "/error/alert";
    }
}
