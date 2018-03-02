package com.adinstar.pangyo.controller.exception;

import com.adinstar.pangyo.constant.PangyoErrorMessage;

public class UnauthorizedException extends RuntimeException {
    public static final UnauthorizedException NEED_LOGIN = new UnauthorizedException(PangyoErrorMessage.NEED_LOGIN);
    public static final UnauthorizedException NO_OWNER_SHIP = new UnauthorizedException(PangyoErrorMessage.NO_OWNER_SHIP);

    public UnauthorizedException(String msg) {
        super(msg);
    }
}
