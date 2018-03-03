package com.adinstar.pangyo.mapper;

import com.adinstar.pangyo.model.Star;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface StarMapper {
    Star selectById(long id);
}
