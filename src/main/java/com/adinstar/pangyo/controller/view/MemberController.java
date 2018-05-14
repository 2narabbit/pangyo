package com.adinstar.pangyo.controller.view;

import com.adinstar.pangyo.constant.PangyoAuthorizedKey;
import com.adinstar.pangyo.constant.ViewModelName;
import com.adinstar.pangyo.controller.exception.UnauthorizedException;
import com.adinstar.pangyo.controller.interceptor.LoginInterceptor;
import com.adinstar.pangyo.model.LoginInfo;
import com.adinstar.pangyo.model.User;
import com.adinstar.pangyo.model.authorization.KOauthInfo;
import com.adinstar.pangyo.model.authorization.KakaoLoginInfo;
import com.adinstar.pangyo.service.KakaoLoginService;
import com.adinstar.pangyo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.util.WebUtils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
@RequestMapping("/member")
public class MemberController {

    public static final String HOME_URL = "/star/list";

    @Autowired
    private KakaoLoginService kakaoLoginService;

    @Autowired
    private UserService userService;

    @RequestMapping(value = {"", "/login"}, method = RequestMethod.GET)
    public String login(HttpServletRequest request,
                        @ModelAttribute(ViewModelName.AUTH) LoginInfo userInfo,
                        @RequestParam(value = "continue", required = false) String continueUrl) {
        if (continueUrl != null) {
            WebUtils.setSessionAttribute(request, PangyoAuthorizedKey.CONTINUE, continueUrl);
        }

        if (userInfo != null) {
            return "redirect:" + HOME_URL;     // welcome 페이지를 만들까??
        }

        return "member/login";
    }

    @RequestMapping(value = {"/oauth"}, method = RequestMethod.GET)
    public String getToken(HttpServletRequest request,
                           HttpServletResponse response,
                           @RequestParam("code") String code) {
        KOauthInfo kOauthInfo = kakaoLoginService.getKOauthInfo(code);
        if (kOauthInfo == null) {
            throw UnauthorizedException.NEED_AUTH_CODE;
        }

        LoginInterceptor.addCookie(request, response, PangyoAuthorizedKey.ACCESS_TOKEN, kOauthInfo.getAccessToken());

        KakaoLoginInfo kakaoLoginInfo = kakaoLoginService.getKakaoLoginInfo(kOauthInfo.getAccessToken());
        if (kakaoLoginInfo == null) {
            throw UnauthorizedException.NEED_SIGNUP;
        }

        User user = userService.get(kakaoLoginInfo.getService(), kakaoLoginInfo.getId());
        if (user == null) {
            return "redirect:/member/join";
        }

        String continueUrl = (String) WebUtils.getSessionAttribute(request, PangyoAuthorizedKey.CONTINUE);
        if (continueUrl == null) {
            return "redirect:" + HOME_URL;
        }

        return "redirect:" + continueUrl;
    }

    @RequestMapping(value = "/join", method = RequestMethod.GET)
    public String join(HttpServletRequest request, Model model) {
        Cookie accessTokenCookie = WebUtils.getCookie(request, PangyoAuthorizedKey.ACCESS_TOKEN);   // Access_token 암호화 해서 저장할 때, 서비스가 정보도 함께 묶어서 저장하도록 하자!
        if (accessTokenCookie == null
                || StringUtils.isEmpty(accessTokenCookie.getValue())
                || kakaoLoginService.isInvalidToken(accessTokenCookie.getValue())) {
            return "redirect:/member/login";
        }

        KakaoLoginInfo kakaoLoginInfo = kakaoLoginService.getKakaoLoginInfo(accessTokenCookie.getValue());
        if (kakaoLoginInfo == null) {
            throw UnauthorizedException.NEED_SIGNUP;
        }

        String continueUrl = (String) WebUtils.getSessionAttribute(request, PangyoAuthorizedKey.CONTINUE);

        model.addAttribute("nickname", kakaoLoginInfo.getProperties().getNickname());
        model.addAttribute("profileImg", kakaoLoginInfo.getProperties().getProfileImage());
        model.addAttribute("service", kakaoLoginInfo.getService());
        model.addAttribute("continueUrl", continueUrl == null ? HOME_URL : continueUrl);
        return "member/join";
    }

    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public String logout() {
        return "member/logout";
    }
}
