package com.adinstar.pangyo.exception;

import com.adinstar.pangyo.constant.PangyoErrorMessage;

// 이부분도 나래님과 이야기 하도록 하자. 비지니스 로직이 명확하게 동작 했을 때 발생할 수 없는 상황에 대한 exception인데
// exception 패키지를 따로 둘까?? 이건 사용자 요청과 전혀 무관한 exception인데ㅠㅠㅠ
public class InvalidSituation extends RuntimeException {
    public static final InvalidSituation NOT_FOUND_EXECUTION_RULE = new InvalidSituation(PangyoErrorMessage.NOT_FOUND_EXECUTION_RULE);
    public static final InvalidSituation NOT_FOUND_POLICY = new InvalidSituation(PangyoErrorMessage.NOT_FOUND_POLICY);
    public static final InvalidSituation NOT_FOUND_RUNNING_TURN = new InvalidSituation(PangyoErrorMessage.NOT_FOUND_RUNNING_TURN);

    public InvalidSituation(String msg) {
        super(msg);
    }
}
