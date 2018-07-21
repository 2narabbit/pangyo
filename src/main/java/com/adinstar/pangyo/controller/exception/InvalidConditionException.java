package com.adinstar.pangyo.controller.exception;

import com.adinstar.pangyo.constant.PangyoErrorMessage;

public class InvalidConditionException extends RuntimeException {
    public static final InvalidConditionException EXECUTION_RULE = new InvalidConditionException(PangyoErrorMessage.INVALID_EXECUTION_RULE);
    public static final InvalidConditionException POLICY = new InvalidConditionException(PangyoErrorMessage.INVALID_POLICY);
    public static final InvalidConditionException TRUN_NUM = new InvalidConditionException(PangyoErrorMessage.INVALID_TURN_NUM);
    public static final InvalidConditionException LAST_TIME = new InvalidConditionException(PangyoErrorMessage.INVALID_LAST_TIME);
    public static final InvalidConditionException UNSUPPORTED_ANNOTATION = new InvalidConditionException(PangyoErrorMessage.INVALID_UNSUPPORTED_ANNOTATION);
    public static final InvalidConditionException NOT_FOUND_CAMPAIGN_CANDIDATE = new InvalidConditionException(PangyoErrorMessage.NOT_FOUND_CAMPAIGN_CANDIDATE);

    public InvalidConditionException(String msg) {
        super(msg);
    }
}
