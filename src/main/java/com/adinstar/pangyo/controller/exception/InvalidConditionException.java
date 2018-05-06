package com.adinstar.pangyo.controller.exception;

import com.adinstar.pangyo.constant.PangyoErrorMessage;

public class InvalidConditionException extends RuntimeException {
    public static final InvalidConditionException EXECUTION_RULE = new InvalidConditionException(PangyoErrorMessage.INVALID_EXECUTION_RULE);

    public InvalidConditionException(String msg) {
        super(msg);
    }
}
