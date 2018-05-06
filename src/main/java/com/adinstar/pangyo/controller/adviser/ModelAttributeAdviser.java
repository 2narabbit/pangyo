package com.adinstar.pangyo.controller.adviser;

import com.adinstar.pangyo.constant.ViewModelName;
import com.adinstar.pangyo.model.LoginInfo;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import javax.servlet.http.HttpServletRequest;

@ControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
public class ModelAttributeAdviser {

    @ModelAttribute(ViewModelName.AUTH)
    public LoginInfo setLoginUserModel(HttpServletRequest request) {
        return (LoginInfo) request.getAttribute(ViewModelName.AUTH);
    }
}
