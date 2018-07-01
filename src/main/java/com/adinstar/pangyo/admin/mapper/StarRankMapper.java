package com.adinstar.pangyo.admin.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

@Mapper
@Repository
public interface StarRankMapper {
    LocalDateTime selectLastTime();

    void insert(@Param("starId") long starId, @Param("time") String time, @Param("ranking") long ranking);
}
