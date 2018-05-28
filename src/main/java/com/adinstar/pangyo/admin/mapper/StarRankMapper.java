package com.adinstar.pangyo.admin.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.time.LocalDateTime;

@Mapper
public interface StarRankMapper {
    LocalDateTime selectLastTime();

    void insert(@Param("starId") long starId, @Param("time") String time, @Param("ranking") long ranking);
}
