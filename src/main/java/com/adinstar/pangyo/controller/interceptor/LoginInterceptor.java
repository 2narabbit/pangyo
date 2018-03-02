package com.adinstar.pangyo.controller.interceptor;

import com.adinstar.pangyo.common.authentication.LoginInfo;
import com.adinstar.pangyo.constant.ViewModelName;
import com.adinstar.pangyo.controller.exception.UnauthorizedException;
import com.adinstar.pangyo.controller.interceptor.annotation.MustLogin;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class LoginInterceptor extends HandlerInterceptorAdapter {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        LoginInfo loginInfo = new LoginInfo();  // 로그인 오브젝트를 어떻게 관리할 지 고민해 보도록 하쟈규!
        loginInfo.setId(100L);
        request.setAttribute(ViewModelName.AUTH, loginInfo);

        if (handler != null && handler instanceof HandlerMethod) {
            HandlerMethod handlerMethod = (HandlerMethod) handler;
            MustLogin mustLoginAnno = handlerMethod.getMethodAnnotation(MustLogin.class);
            if (mustLoginAnno == null) {
                return true;
            }

            if (loginInfo == null){
                throw UnauthorizedException.NEED_LOGIN;
            }
        }
        return true;
    }
//
//    @Override
//    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
//
//    }
//
//    @Override
//    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
//
//    }
}
