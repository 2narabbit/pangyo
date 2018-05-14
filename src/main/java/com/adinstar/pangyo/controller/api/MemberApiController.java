package com.adinstar.pangyo.controller.api;

import com.adinstar.pangyo.constant.ViewModelName;
import com.adinstar.pangyo.model.LoginInfo;
import com.adinstar.pangyo.model.User;
import com.adinstar.pangyo.model.authorization.KOauthInfo;
import com.adinstar.pangyo.model.authorization.KakaoLoginInfo;
import com.adinstar.pangyo.service.KakaoLoginService;
import com.adinstar.pangyo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/member")
public class MemberApiController {

    @Autowired
    private KakaoLoginService kakaoLoginService;

    @Autowired
    private UserService userService;

    @RequestMapping(value = {"/logout"}, method = RequestMethod.GET)
    public void logout(@RequestParam("accessToken") String accessToken) {
        kakaoLoginService.logout(accessToken);
    }

    @RequestMapping(value = {"/join"}, method = RequestMethod.POST)
    public void join(@RequestBody User user) {
        userService.add(user);
    }

    @RequestMapping(value = {"/recommandCode"}, method = RequestMethod.GET)
    public boolean join(@RequestParam String recommandCode) {
        return userService.isValidRecommandCode(recommandCode);
    }

    @RequestMapping(value = {"/signup"}, method = RequestMethod.GET)
    public String signup(@RequestParam("accessToken") String accessToken) {
        return kakaoLoginService.signup(accessToken);
    }

    @RequestMapping(value = {"/unlink"}, method = RequestMethod.GET)
    public String unlink(@RequestParam("accessToken") String accessToken) {
        return kakaoLoginService.unlink(accessToken);
    }

    @RequestMapping(value = {"/loginInfo"}, method = RequestMethod.GET)
    public KakaoLoginInfo getLoginInfo(@RequestParam("accessToken") String accessToken) {
        return kakaoLoginService.getKakaoLoginInfo(accessToken);
    }

    @RequestMapping(value = {"/refreshToken"}, method = RequestMethod.GET)
    public KOauthInfo refreshToken(@RequestParam("refreshToken") String refreshToken) {
        return kakaoLoginService.refreshToken(refreshToken);
    }
}