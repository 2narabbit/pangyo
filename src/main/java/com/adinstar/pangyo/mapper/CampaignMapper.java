package com.adinstar.pangyo.mapper;

import com.adinstar.pangyo.model.Campaign;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface CampaignMapper {
    List<Long> selectCampaignIdListOrderBySupportCount(@Param("offset") long offset, @Param("size") int size);

    Campaign selectById(long id);

    int updateViewCount(@Param("id") long id, @Param("delta") int delta);

    long selectRankingByIdAndExecuteRuleId(@Param("id") long id, @Param("executeRuleId") long executeRuleId);

    List<Campaign> selectListByExecuteRuleId(@Param("executeRuleId") long executeRuleId, @Param("offset") long offset, @Param("size") int size);
}
