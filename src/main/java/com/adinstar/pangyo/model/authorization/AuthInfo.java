package com.adinstar.pangyo.model.authorization;

import com.adinstar.pangyo.constant.PangyoEnum;

public interface AuthInfo {
    PangyoEnum.AccountType getService();
    String getAccessToken();
}
