package com.adinstar.pangyo.model;

import com.adinstar.pangyo.model.authorization.LoginInfo;
import lombok.Data;

@Data
public class ViewerInfo {
    private User user;
    private LoginInfo loginInfo;

    public ViewerInfo(User user, LoginInfo loginInfo) {
        this.user = user;
        this.loginInfo = loginInfo;
    }

    public long getId() {
        return user.getId();
    }
}
