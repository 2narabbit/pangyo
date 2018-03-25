package com.adinstar.pangyo.controller.interceptor;

import com.adinstar.pangyo.common.annotation.MustLogin;
import com.adinstar.pangyo.constant.ViewModelName;
import com.adinstar.pangyo.controller.exception.UnauthorizedException;
import com.adinstar.pangyo.model.LoginInfo;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class LoginInterceptor extends HandlerInterceptorAdapter {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        LoginInfo loginInfo = new LoginInfo();  // TODO : 로그인 오브젝트를 어떻게 관리할 지 고민해 보도록 하쟈규!
        loginInfo.setId(100L);
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
}
