package com.adinstar.pangyo.mapper;

import com.adinstar.pangyo.model.Image;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ImageMapper {
    Image selectById(String id);
    int insert(Image image);
}
