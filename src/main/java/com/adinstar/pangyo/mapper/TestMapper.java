package com.adinstar.pangyo.mapper;

import com.adinstar.pangyo.model.Test;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface TestMapper {
    Test select(Integer val1);
}
