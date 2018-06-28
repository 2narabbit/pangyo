package com.adinstar.pangyo.service;

import com.adinstar.pangyo.constant.PangyoEnum;
import com.adinstar.pangyo.mapper.UserMapper;
import com.adinstar.pangyo.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserMapper userMapper;

    public User get(PangyoEnum.AccountType service, long serviceUserId) {
        return userMapper.selectByServiceAndServiceUserId(service.name(), serviceUserId);
    }

    public void add(User user) {
        userMapper.insert(user);
    }

    public Boolean isValidRecommendCode(String recommendCode) {
        long recommendUserId;
        try {
            recommendUserId = Long.valueOf(recommendCode) - User.MAGIC_NUMBER;  // todo: 추천인 코드는 어떻게 제공하면 좋을지 고민하자... 걍 id로?
        } catch (Exception e) {
            return false;
        }

        if (recommendUserId <= 0) {
            return false;
        }

        return (userMapper.selectById(recommendUserId) == null) ? false : true;
    }

    public void withdrawal(long id) {
        userMapper.updateStatus(id, PangyoEnum.UserStatus.DELETED);
    }

    public boolean isUsableName(String name) {
        return (userMapper.selectByName(name) == null) ? true : false;
    }

    public void modify(User user) {
        userMapper.update(user);
    }
}