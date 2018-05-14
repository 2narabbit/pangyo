package com.adinstar.pangyo.controller.interceptor;

import com.adinstar.pangyo.common.annotation.MustLogin;
import com.adinstar.pangyo.constant.ViewModelName;
import com.adinstar.pangyo.controller.exception.UnauthorizedException;
import com.adinstar.pangyo.model.LoginInfo;
import com.adinstar.pangyo.model.authorization.KOauthInfo;
import com.adinstar.pangyo.model.authorization.KakaoLoginInfo;
import com.adinstar.pangyo.service.KakaoLoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import org.springframework.web.util.WebUtils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class LoginInterceptor extends HandlerInterceptorAdapter {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        LoginInfo loginInfo = getLoginInfo();
        request.setAttribute(ViewModelName.AUTH, loginInfo);

        if (handler != null && handler instanceof HandlerMethod) {
            HandlerMethod handlerMethod = (HandlerMethod) handler;
            MustLogin mustLoginAnno = handlerMethod.getMethodAnnotation(MustLogin.class);
            if (mustLoginAnno == null) {
                return true;
            }

            if (loginInfo != null) {
                return true;
            }

            if (request.getRequestURI().contains("/api")) {
                throw UnauthorizedException.NEED_LOGIN;
            } else {
                // redirect LoginPage();
            }
        }

        return true;
    }

    @Autowired
    private KakaoLoginService kakaoLoginService;

    // 이부분은 나중에 나이스하게 풀어보자;;ㅎ
    private LoginInfo getLoginInfo() {
//        KOauthInfo kOauthInfo = kakaoLoginService.getKOauthInfo(code);
//        if (kOauthInfo != null) {
//            KakaoLoginInfo kakaoLoginInfo = kakaoLoginService.getKakaoLoginInfo(kOauthInfo.getAccessToken());
//        }
//        model.addAttribute("kOauthInfo", kOauthInfo);
//        Random random = new Random();
//        long id = random.nextInt(4);
//        if (0 < id && id < 2) {
//            LoginInfo loginInfo = new LoginInfo();
//            loginInfo.setId(id);
//            return loginInfo;
//        }
        return null;

//        LoginInfo loginInfo = new LoginInfo();
//        loginInfo.setId(1L);
//        return loginInfo;
    }

    public static void addCookie(HttpServletRequest request, HttpServletResponse response, String name, String value) {
        Cookie cookie = WebUtils.getCookie(request, name);
        if (cookie == null) {
            cookie = new Cookie(name, value);
        }
        cookie.setValue(value);
        cookie.setMaxAge(24 * 60 * 60);   // 하루?
        cookie.setPath("/");
        response.addCookie(cookie);
    }
}
