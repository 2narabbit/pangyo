package com.adinstar.pangyo.model.authorization.kakao;

import com.adinstar.pangyo.constant.PangyoEnum.AccountType;
import com.adinstar.pangyo.model.authorization.LoginInfo;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonSetter;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class KakaoLoginInfo implements LoginInfo {
    private long id;
    private String kaccountEmail;
    private Boolean verified;
    private KProperties properties;

    @JsonSetter("kaccount_email")
    public void setKaccountEmail(String kaccountEmail) {
        this.kaccountEmail = kaccountEmail;
    }

    @JsonSetter("kaccount_email_verified")
    public void setVerified(Boolean verified) {
        this.verified = verified;
    }

    @Override
    public long getId() {
        return id;
    }

    @Override
    public AccountType getService() {
        return AccountType.KAKAO;
    }

    @Override
    public String getNickname() {
        return (properties == null) ? null : properties.getNickname();
    }

    @Override
    public String getProfileImage() {
        return (properties == null) ? null : properties.getProfileImage();
    }
}
