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
}
