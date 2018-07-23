package com.adinstar.pangyo.controller.interceptor;

import com.adinstar.pangyo.common.annotation.MustLogin;
import com.adinstar.pangyo.constant.PangyoAuthorizedKey;
import com.adinstar.pangyo.constant.PangyoEnum.AccountType;
import com.adinstar.pangyo.constant.ViewModelName;
import com.adinstar.pangyo.controller.exception.UnauthorizedException;
import com.adinstar.pangyo.model.User;
import com.adinstar.pangyo.model.ViewerInfo;
import com.adinstar.pangyo.model.authorization.AuthInfo;
import com.adinstar.pangyo.model.authorization.LoginInfo;
import com.adinstar.pangyo.service.LoginService;
import com.adinstar.pangyo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import org.springframework.web.util.WebUtils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

@Component
@Order(1)
public class LoginInterceptor extends HandlerInterceptorAdapter {

    private static final int ONE_DAY = 24 * 60 * 60;

    @Autowired
    private LoginService loginService;

    @Autowired
    private UserService userService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        LoginInfo loginInfo = getLoginInfo(request, response);
        request.setAttribute(ViewModelName.AUTH, loginInfo);

        ViewerInfo viewerInfo = getViwerLoginInfo(loginInfo);
        request.setAttribute(ViewModelName.VIEWER, viewerInfo);

        if (handler != null && handler instanceof HandlerMethod) {
            HandlerMethod handlerMethod = (HandlerMethod) handler;

            MustLogin mustFanClass = handlerMethod.getMethod().getDeclaringClass().getAnnotation(MustLogin.class);
            if (mustFanClass != null) {
                if (viewerInfo == null) {
                    throw UnauthorizedException.NEED_LOGIN;
                }
            }

            MustLogin mustLoginMethod = handlerMethod.getMethodAnnotation(MustLogin.class);
            if (mustLoginMethod != null) {
                if (viewerInfo == null) {
                    throw UnauthorizedException.NEED_LOGIN;
                }
            }
        }

        return true;
    }

    private LoginInfo getLoginInfo(HttpServletRequest request, HttpServletResponse response) {
        Map authMap = getAuthInfoByCookie(request);
        if (authMap == null) {
            return null;
        }

        AccountType accountType = (AccountType) authMap.get(PangyoAuthorizedKey.SERVICE);
        String accessToken = (String) authMap.get(PangyoAuthorizedKey.ACCESS_TOKEN);

        if (loginService.isInvalidToken(accountType, accessToken)) {
            expireCookieOfAccessToken(request, response);
            return null;
        }

        LoginInfo loginInfo = loginService.getLoginInfo(accountType, accessToken);
        if (loginInfo == null) {
            return null;
        }

        return loginInfo;
    }

    private ViewerInfo getViwerLoginInfo(LoginInfo loginInfo) {
        if (loginInfo == null) {
            return null;
        }

        User user = userService.get(loginInfo.getService(), loginInfo.getId());
        if (user == null) {
            return null;
        }

        return new ViewerInfo(user, loginInfo);
    }


    //////// TODO : 향후 암호화 부분을 유틸로 뺄지도 고민하자!!!!!!!! //////////////////
    private static String encrypt(String data) {
        // TODO : 나중에 여기 암호화 모듈 추가해줘!!! 내가 봤을 때... map으로 저장해야지 않을까? service / token 정보를?? 힝 ㅠㅠ 몰랑.
        return data;
    }

    private static String decrypt(String data) {
        // TODO : 나중에 여기 복호화 모듈 추가해줘!!!
        return data;
    }

    public static void addCookieOfAccessToken(HttpServletRequest request, HttpServletResponse response, AuthInfo authInfo) {
        Cookie cookie = WebUtils.getCookie(request, PangyoAuthorizedKey.AUTH_COOKIE_NAME);
        if (cookie == null) {
            cookie = new Cookie(PangyoAuthorizedKey.AUTH_COOKIE_NAME, encrypt(authInfo.getService().name() + PangyoAuthorizedKey.SEPARATE + authInfo.getAccessToken()));
        }
        cookie.setMaxAge(ONE_DAY);
        cookie.setPath("/");
        response.addCookie(cookie);
    }

    public static Map<String, Object> getAuthInfoByCookie(HttpServletRequest request) {
        Cookie cookie = WebUtils.getCookie(request, PangyoAuthorizedKey.AUTH_COOKIE_NAME);
        if (cookie == null) {
            return null;
        }

        String data = decrypt(cookie.getValue());
        if (data == null) {
            return null;
        }

        String[] authArray = data.split(PangyoAuthorizedKey.SEPARATE);
        AccountType accountType = null;
        if (AccountType.KAKAO.name().equals(authArray[0])) {
            accountType = AccountType.KAKAO;
        }

        Map<String, Object> auth = new HashMap<>();
        auth.put(PangyoAuthorizedKey.SERVICE, accountType);
        auth.put(PangyoAuthorizedKey.ACCESS_TOKEN, authArray[1]);
        return auth;
    }

    public static void expireCookieOfAccessToken(HttpServletRequest request, HttpServletResponse response) {
        Cookie cookie = WebUtils.getCookie(request, PangyoAuthorizedKey.AUTH_COOKIE_NAME);
        if (cookie != null) {
            cookie.setMaxAge(0);
            cookie.setPath("/");
            response.addCookie(cookie);
        }
    }
}