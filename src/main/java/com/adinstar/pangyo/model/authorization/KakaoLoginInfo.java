package com.adinstar.pangyo.model.authorization;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonSetter;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class KakaoLoginInfo {
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
}
