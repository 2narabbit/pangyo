package com.adinstar.pangyo.model.authorization.kakao;

import com.adinstar.pangyo.constant.PangyoEnum.AccountType;
import com.adinstar.pangyo.model.authorization.AuthInfo;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonSetter;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class KOauthInfo implements AuthInfo {
    private String accessToken;
    private String tokenType;
    private String refreshToken;
    private long expiresIn;
    private String scope;

    @JsonSetter("access_token")
    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    @JsonSetter("token_type")
    public void setTokenType(String tokenType) {
        this.tokenType = tokenType;
    }

    @JsonSetter("refresh_token")
    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    @JsonSetter("expires_in")
    public void setExpiresIn(long expiresIn) {
        this.expiresIn = expiresIn;
    }

    @Override
    public AccountType getService() {
        return AccountType.KAKAO;
    }
}
