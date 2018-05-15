package com.adinstar.pangyo.service;

import com.adinstar.pangyo.constant.PangyoEnum.AccountType;
import com.adinstar.pangyo.model.authorization.AuthInfo;
import com.adinstar.pangyo.model.authorization.LoginInfo;
import com.adinstar.pangyo.model.authorization.kakao.KTokenInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LoginService {

    @Autowired
    private KakaoLoginService kakaoLoginService;  // todo:  다른 서비스를 붙이더라도 여기서 확안되어야 할 텐데;

    public boolean isInvalidToken(AccountType type, String accessToken) {
        switch (type) {
            case KAKAO:
                return isInvalidKakaoToken(accessToken);  // 허허 세상 구리다!
            default:
                return false;
        }
    }

    public AuthInfo getAuthInfo(AccountType type, String code) {
        switch (type) {
            case KAKAO:
                return kakaoLoginService.getKOauthInfo(code);
            default:
                return null;
        }
    }

    public LoginInfo getLoginInfo(AccountType type, String accessToken) {
        switch (type) {
            case KAKAO:
                return kakaoLoginService.getKakaoLoginInfo(accessToken);
            default:
                return null;
        }
    }

    private boolean isInvalidKakaoToken(String accessToken) {
        try {
            KTokenInfo kTokenInfo = kakaoLoginService.getKTokenInfo(accessToken);
            return (kTokenInfo == null) ? true : false;
        } catch (Exception e) {
            return true;
        }
    }

    public void unlink(AccountType type, String accessToken) {
        switch (type) {
            case KAKAO:
                kakaoLoginService.unlink(accessToken);
                break;
            default:
                break;
        }
    }
}
