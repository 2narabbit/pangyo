package com.adinstar.pangyo.common.aspect;

import com.adinstar.pangyo.common.annotation.CheckAuthority;
import com.adinstar.pangyo.model.ViewerInfo;

import java.lang.reflect.Method;

public interface AuthorityStrategy {
    boolean isValid(ViewerInfo viewerInfo, Method method, Object[] args, CheckAuthority checkAuthority);
}
