package com.adinstar.pangyo.common.aspect;

import com.adinstar.pangyo.common.annotation.CheckAuthority;
import com.adinstar.pangyo.model.LoginInfo;

import java.lang.reflect.Method;

public interface AuthorityStrategy {
    boolean isValid(LoginInfo loginInfo, Method method, Object[] args, CheckAuthority checkAuthority);
}
