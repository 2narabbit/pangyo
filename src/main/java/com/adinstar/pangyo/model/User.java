package com.adinstar.pangyo.model;

import com.adinstar.pangyo.constant.PangyoEnum.*;
import lombok.Data;

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

    public String getName() {
        if (name == null) {
            return "무명";
        } else {
            return name;
        }
    }

    public String getProfileImg() {
        if (profileImg == null) {
            return "http://t1.daumcdn.net/profile/TfdXX_AUCLw0";
        } else {
            return profileImg;
        }
    }
}
