package com.adinstar.pangyo.model.authorization;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonSetter;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class KOauthInfo {
    private String accessToken;
    private String tokenType;
    private String refreshToken;
    private long expiresIn;
    private String scope;
    private LocalDateTime loginDateTime;

    @JsonSetter("access_token")
    public void setAccessToken(String accessToken){
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

    public boolean isExpired() {
        LocalDateTime expiredDateTime = loginDateTime.plusSeconds(expiresIn);
        if (expiredDateTime.isBefore(LocalDateTime.now())) {
            return true;
        }
        return false;
    }
}
