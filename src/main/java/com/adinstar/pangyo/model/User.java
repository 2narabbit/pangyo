package com.adinstar.pangyo.model;

import com.adinstar.pangyo.constant.PangyoEnum.*;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import org.springframework.util.StringUtils;

@Data
public class User {
    @JsonIgnoreProperties
    public static final long MAGIC_NUMBER = 1_000_000L;

    private long id;
    private String service;
    private String serviceUserId;
    private String name;
    private String profileImg;
    private String recommendCode;
    private UserStatus status;
    private PangyoLocalDataTime dateTime;

    public String getProfileImg() {
        if (StringUtils.isEmpty(profileImg)) {
            // TODO: noImage url 변경
            return "http://t1.daumcdn.net/profile/TfdXX_AUCLw0";
        } else {
            return profileImg;
        }
    }

    public String getMyCode(){
        return String.valueOf(id + MAGIC_NUMBER);
    }
}
