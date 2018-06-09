package com.adinstar.pangyo.controller.exception;

import com.adinstar.pangyo.constant.PangyoErrorMessage;

public class NotFoundException extends RuntimeException {
    public static final NotFoundException CAMPAIGN_CANDIDATE = new NotFoundException(PangyoErrorMessage.NOT_FOUND_CAMPAIGN_CANDIDATE);
    public static final NotFoundException STAR = new NotFoundException(PangyoErrorMessage.NOT_FOUND_STAR);
    public static final NotFoundException ACTION_HISTORY = new NotFoundException(PangyoErrorMessage.NOT_FOUND_ACTION_HISTORY);
    public static final NotFoundException POST = new NotFoundException(PangyoErrorMessage.NOT_FOUND_POST);
    public static final NotFoundException COMMENT = new NotFoundException(PangyoErrorMessage.NOT_FOUND_COMMENT);

    public NotFoundException(String msg) {
        super(msg);
    }
}
