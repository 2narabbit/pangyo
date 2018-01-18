package com.adinstar.pangyo.service;

import com.adinstar.pangyo.mapper.TestMapper;
import com.adinstar.pangyo.model.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TestService {
    @Autowired
    private TestMapper testMapper;

    public Test get(Integer val1) {
        return testMapper.select(val1);
    }
}
