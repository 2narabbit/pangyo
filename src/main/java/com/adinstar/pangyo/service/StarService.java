package com.adinstar.pangyo.service;

import com.adinstar.pangyo.mapper.StarMapper;
import com.adinstar.pangyo.model.Star;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StarService {

    @Autowired
    private StarMapper starMapper;

    public Star getById(long id) {
        return starMapper.selectById(id);
    }
}
