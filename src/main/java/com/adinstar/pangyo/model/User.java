package com.adinstar.pangyo.model;

import com.adinstar.pangyo.constant.PangyoEnum.*;
import lombok.Data;
import org.springframework.util.StringUtils;

@Data
public class User {
    private long id;
    private String service;
    private String serviceUserId;
    private String name;
    private String profileImg;
    private String recommandCode;
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
}
