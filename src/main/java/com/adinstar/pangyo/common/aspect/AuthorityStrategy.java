package com.adinstar.pangyo.common.aspect;

import com.adinstar.pangyo.common.annotation.CheckAuthority;
import com.adinstar.pangyo.model.ViwerInfo;

import java.lang.reflect.Method;

public interface AuthorityStrategy {
    boolean isValid(ViwerInfo viwerInfo, Method method, Object[] args, CheckAuthority checkAuthority);
}
