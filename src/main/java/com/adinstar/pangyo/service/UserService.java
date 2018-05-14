package com.adinstar.pangyo.service;

import com.adinstar.pangyo.mapper.UserMapper;
import com.adinstar.pangyo.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserMapper userMapper;

    public User get(String service, long serviceUserId) {
        return userMapper.selectByServiceAndServiceUserId(service, serviceUserId);
    }

    public void add(User user) {
        userMapper.insert(user);
    }

    // TODO : 서비스랑 유저id 랑 어떻게 잘 mix해서 valid를 체크할지 고민하자!
    public boolean isValidRecommandCode(String recommandCode) {
        return true;
    }
}