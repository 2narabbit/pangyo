package com.adinstar.pangyo.admin.mapper;

import com.adinstar.pangyo.model.RankData;
import com.adinstar.pangyo.model.Star;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Mapper
@Repository
public interface StarRankMapper {
    LocalDateTime selectLastTime();

    void insert(@Param("starId") long starId, @Param("time") String time, @Param("ranking") long ranking);

    List<RankData<Star>> selectStarRankList(@Param("rankId") long rankId, @Param("time") LocalDateTime time, @Param("size") int size);

    List<RankData<Star>> selectJoinedStarRankListByUserId(@Param("userId") long userId, @Param("rankId") long rankId, @Param("time") LocalDateTime time, @Param("size") int size);

    List<RankData<Star>> selectNotJoinedStarRankListByUserId(@Param("userId") long userId, @Param("rankId") long rankId, @Param("time") LocalDateTime time, @Param("size") int size);
}
