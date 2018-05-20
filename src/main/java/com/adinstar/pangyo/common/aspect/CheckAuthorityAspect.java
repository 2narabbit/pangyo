package com.adinstar.pangyo.common.aspect;

import com.adinstar.pangyo.common.annotation.CheckAuthority;
import com.adinstar.pangyo.constant.ViewModelName;
import com.adinstar.pangyo.controller.exception.UnauthorizedException;
import com.adinstar.pangyo.model.ViwerInfo;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;

@Aspect
@Component
public class CheckAuthorityAspect {

    @Autowired
    private AuthorityStrategyFactory authorityStrategyFactory;

    // TODO : api일 때 핸들러랑, page일 때 error 페이지랑 구분해서 처리되도록 하기!!!!
    @Around("@annotation(com.adinstar.pangyo.common.annotation.CheckAuthority)")
    public Object checkAuthority(ProceedingJoinPoint joinPoint) throws Throwable {
        ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = servletRequestAttributes.getRequest();

        ViwerInfo viwerInfo = (ViwerInfo) request.getAttribute(ViewModelName.VIEWER);
        if (viwerInfo == null) {
            throw UnauthorizedException.NEED_LOGIN;
        }

        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        CheckAuthority checkAuthorityAnno = method.getAnnotation(CheckAuthority.class);

        authorityStrategyFactory.getInstance(checkAuthorityAnno.type()).isValid(viwerInfo, method, joinPoint.getArgs(), checkAuthorityAnno);

        return joinPoint.proceed();
    }
}