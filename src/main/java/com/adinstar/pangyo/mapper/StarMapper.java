package com.adinstar.pangyo.mapper;

import com.adinstar.pangyo.model.Star;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface StarMapper {
    Star selectById(long id);

    void insert(Star star);

    void update(Star star);

    int insertJoin(@Param("starId") long starId, @Param("userId") long userId);

    int deleteJoin(@Param("starId") long starId, @Param("userId") long userId);

    void updateFanCount(@Param("starId") long starId, @Param("delta") int delta);

    List<Star> selectListOrderByFanCount(@Param("offset") long offset, @Param("size") int size);

    List<Star> selectListByUserIdAndStarId(@Param("userId") long userId, @Param("starId") Long starId);
}