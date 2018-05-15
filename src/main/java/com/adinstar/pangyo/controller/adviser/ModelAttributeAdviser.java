package com.adinstar.pangyo.controller.adviser;

import com.adinstar.pangyo.constant.ViewModelName;
import com.adinstar.pangyo.model.ViwerInfo;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import javax.servlet.http.HttpServletRequest;

@ControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
public class ModelAttributeAdviser {

    @ModelAttribute(ViewModelName.VIEWER)
    public ViwerInfo setLoginUserModel(HttpServletRequest request) {
        return (ViwerInfo) request.getAttribute(ViewModelName.VIEWER);
    }
}
