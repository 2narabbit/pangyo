package com.adinstar.pangyo.controller.view;

import com.adinstar.pangyo.common.annotation.MustLogin;
import com.adinstar.pangyo.constant.PangyoAuthorizedKey;
import com.adinstar.pangyo.constant.PangyoEnum;
import com.adinstar.pangyo.constant.ViewModelName;
import com.adinstar.pangyo.controller.exception.UnauthorizedException;
import com.adinstar.pangyo.controller.interceptor.LoginInterceptor;
import com.adinstar.pangyo.model.User;
import com.adinstar.pangyo.model.ViewerInfo;
import com.adinstar.pangyo.model.authorization.AuthInfo;
import com.adinstar.pangyo.model.authorization.LoginInfo;
import com.adinstar.pangyo.service.LoginService;
import com.adinstar.pangyo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.util.WebUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
@RequestMapping("/member")
public class MemberController {

    public static final String HOME_URL = "/star";

    @Autowired
    private LoginService loginService;

    @Autowired
    private UserService userService;

    @RequestMapping(value = {"", "/login"}, method = RequestMethod.GET)
    public String login(HttpServletRequest request,
                        @ModelAttribute(ViewModelName.VIEWER) ViewerInfo viewerInfo,
                        @RequestParam(value = "continue", required = false) String continueUrl) {
        if (continueUrl != null) {
            WebUtils.setSessionAttribute(request, PangyoAuthorizedKey.CONTINUE, continueUrl);
        }

        if (viewerInfo != null) {
            return "redirect:" + HOME_URL;     // welcome 페이지를 만들까??
        }

        return "member/login";
    }

    @RequestMapping(value = {"/oauth"}, method = RequestMethod.GET)  //kauth로 변경해야지!
    public String getToken(HttpServletRequest request,
                           HttpServletResponse response,
                           @RequestParam("code") String code) {
        AuthInfo authInfo = loginService.getAuthInfo(PangyoEnum.AccountType.KAKAO, code);
        if (authInfo == null) {
            throw UnauthorizedException.NEED_AUTH_CODE;
        }

        LoginInterceptor.addCookieOfAccessToken(request, response, authInfo);

        LoginInfo kakaoLoginInfo = loginService.getLoginInfo(PangyoEnum.AccountType.KAKAO, authInfo.getAccessToken());
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
    public String join(HttpServletRequest request, @ModelAttribute(ViewModelName.AUTH) LoginInfo loginInfo, Model model) {
        if (loginInfo == null) {
            return "redirect:/member/login";
        }

        String continueUrl = (String) WebUtils.getSessionAttribute(request, PangyoAuthorizedKey.CONTINUE);

        model.addAttribute("nickname", loginInfo.getNickname());
        model.addAttribute("profileImg", loginInfo.getProfileImage());
        model.addAttribute("service", loginInfo.getService());
        model.addAttribute("continueUrl", continueUrl == null ? HOME_URL : continueUrl);
        return "member/join";
    }

    @RequestMapping(value = "/withdrawal", method = RequestMethod.GET)
    @MustLogin
    public String withdrawal() {
        return "member/withdrawal";
    }

    @RequestMapping(value = "/myInfo", method = RequestMethod.GET)
    @MustLogin
    public String myInfo() {
        return "member/myInfo";
    }
}