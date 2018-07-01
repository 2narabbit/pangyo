package com.adinstar.pangyo.controller.exception;

import com.adinstar.pangyo.constant.PangyoErrorMessage;


// 이부분도 나래님과 이야기 하도록 하자. 비지니스 로직이 명확하게 동작 했을 때 발생할 수 없는 상황에 대한 exception인데
// exception 패키지를 따로 둘까?? 이건 사용자 요청과 전혀 무관한 exception인데ㅠㅠㅠ
// 이런건 500에러인뎅..ㅎ
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
