package com.adinstar.pangyo.controller.exception;

import com.adinstar.pangyo.constant.PangyoErrorMessage;

public class BadRequestException extends RuntimeException {
    public static final BadRequestException INVALID_PARAM = new BadRequestException(PangyoErrorMessage.INVALID_PARAM);
    public static final BadRequestException DUPLICATE_USER = new BadRequestException(PangyoErrorMessage.DUPLICATE_USER);
    public static final BadRequestException INVALID_PATH = new BadRequestException(PangyoErrorMessage.INVALID_PATH);
    public static final BadRequestException DUPLICATE_POLL = new BadRequestException(PangyoErrorMessage.DUPLICATE_POLL);
    public static final BadRequestException DUPLICATE_CAMPAIGN_CANDIDATE = new BadRequestException(PangyoErrorMessage.DUPLICATE_CAMPAIGN_CANDIDATE);

    public BadRequestException(String msg) {
        super(msg);
    }
}
