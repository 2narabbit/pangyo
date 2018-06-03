package com.adinstar.pangyo.constant;

public class PangyoErrorMessage {
    // UnauthorizedException
    public static final String NEED_LOGIN = "로그인이 필요합니다.";
    public static final String NO_OWNER_SHIP = "권한이 없습니다.";
    public static final String NEED_AUTH_CODE = "인증에 필요한 코드를 찾을 수 없습니다.";
    public static final String NEED_SIGNUP = "로그인 서비스 약관에 미동의 하였습니다.";
    public static final String NEED_JOIN = "팬클럽 가입이 필요합니다.";

    // BadRequestException
    public static final String INVALID_PARAM = "유효하지 않은 파라미터 입니다.";
    public static final String DUPLICATE_USER_REGISTER = "이미 가입되어 있습니다.";
    public static final String INVALID_PATH = "유효하지 않은 주소입니다.";

    // NotFoundException
    public static final String NOT_FOUND_CAMPAIGN_CANDIDATE = "존재하지 않은 후보군 정보입니다.";
    public static final String NOT_FOUND_STAR = "존재하지 않는 팬클럽입니다.";
    public static final String NOT_FOUND_ACTION_HISTORY = "액션 히스토리를 찾을 수 없습니다.";
    public static final String NOT_FOUND_POST = "존재하지 않은 게시글입니다.";
    public static final String NOT_FOUND_COMMENT = "존재하지 않은 댓글입니다.";

    // InvalidSituation
    public static final String INVALID_EXECUTION_RULE = "시스템 내부에 문제가 발생하였습니다(1) 운영자에게 문의주세요!";
    public static final String INVALID_POLICY = "시스템 내부에 문제가 발생하였습니다(2) 운영자에게 문의주세요!";
    public static final String INVALID_TURN_NUM = "시스템 내부에 문제가 발생하였습니다(3) 운영자에게 문의주세요!";
    public static final String INVALID_LAST_TIME = "시스템 내부에 문제가 발생하였습니다(4) 운영자에게 문의주세요!";
}
