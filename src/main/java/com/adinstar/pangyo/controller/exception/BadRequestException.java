package com.adinstar.pangyo.controller.exception;

import com.adinstar.pangyo.constant.PangyoErrorMessage;

public class BadRequestException extends RuntimeException {
    public static final BadRequestException INVALID_PARAM = new BadRequestException(PangyoErrorMessage.INVALID_PARAM);
    public static final BadRequestException DUPLICATE_USER_REGISTER = new BadRequestException(PangyoErrorMessage.DUPLICATE_USER_REGISTER);
    public static final BadRequestException INVALID_PATH = new BadRequestException(PangyoErrorMessage.INVALID_PATH);
    public static final BadRequestException DUPLICATE_POLL = new BadRequestException(PangyoErrorMessage.DUPLICATE_POLL);

    public BadRequestException(String msg) {
        super(msg);
    }
}
