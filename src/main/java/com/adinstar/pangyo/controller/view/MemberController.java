package com.adinstar.pangyo.controller.view;

import com.adinstar.pangyo.constant.ViewModelName;
import com.adinstar.pangyo.model.authorization.KOauthInfo;
import com.adinstar.pangyo.model.LoginInfo;
import com.adinstar.pangyo.service.KakaoLoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/member")
public class MemberController {

    @Autowired
    private KakaoLoginService kakaoLoginService;

    @RequestMapping(value = {"", "/login"}, method = RequestMethod.GET)
    public String login(@ModelAttribute(ViewModelName.AUTH) LoginInfo userInfo) {
        return "member/login";
    }

    @RequestMapping(value = {"/oauth"}, method = RequestMethod.GET)
    public String getToken(@RequestParam("code") String code, Model model) {
        KOauthInfo kOauthInfo = kakaoLoginService.getKOauthInfo(code);
        if (kOauthInfo != null) {
            kakaoLoginService.getKakaoLoginInfo(kOauthInfo.getAccessToken());
        }
        model.addAttribute("kOauthInfo", kOauthInfo);
        return "member/login";
    }
}
