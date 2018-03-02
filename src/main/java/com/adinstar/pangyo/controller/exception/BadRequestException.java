package com.adinstar.pangyo.controller.exception;

import com.adinstar.pangyo.constant.PangyoErrorMessage;

public class BadRequestException extends RuntimeException{
    public static final BadRequestException INVALID_PARAM = new BadRequestException(PangyoErrorMessage.INVALID_PARAM);

    public BadRequestException(String msg) {
        super(msg);
    }
}
