package com.adinstar.pangyo.mapper;

import com.adinstar.pangyo.model.RankData;
import com.adinstar.pangyo.model.Star;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.time.LocalDateTime;
import java.util.List;

@Mapper
public interface StarMapper {
    Star selectById(long id);

    void insert(Star star);

    void update(Star star);

    List<RankData<Star>> selectStarRankList(@Param("rankId") long rankId, @Param("time") LocalDateTime time, @Param("size") int size);

    List<RankData<Star>> selectJoinedStarRankListByUserId(@Param("userId") long userId, @Param("rankId") long rankId, @Param("time") LocalDateTime time, @Param("size") int size);

    List<RankData<Star>> selectNotJoinedStarRankListByUserId(@Param("userId") long userId, @Param("rankId") long rankId, @Param("time") LocalDateTime time, @Param("size") int size);

    void insertJoin(@Param("starId") long starId, @Param("userId") long userId);

    void deleteJoin(@Param("starId") long starId, @Param("userId") long userId);

    void updateFanCount(@Param("starId")long starId, @Param("delta") int delta);
}