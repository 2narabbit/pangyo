package com.adinstar.pangyo.common.annotation;

import com.adinstar.pangyo.constant.PangyoEnum;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface CheckAuthority {
    Class type();

    PangyoEnum.CheckingType checkType();

    boolean isCheckOwner() default true;
}
