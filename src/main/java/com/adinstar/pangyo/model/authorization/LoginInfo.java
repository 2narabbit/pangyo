package com.adinstar.pangyo.model.authorization;

import com.adinstar.pangyo.constant.PangyoEnum;

public interface LoginInfo {
    long getId();
    PangyoEnum.AccountType getService();
    String getNickname();
    String getProfileImage();
}
