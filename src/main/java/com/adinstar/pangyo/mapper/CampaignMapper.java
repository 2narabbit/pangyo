package com.adinstar.pangyo.mapper;

import com.adinstar.pangyo.model.Campaign;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface CampaignMapper {
    List<Campaign> selectCampaignListByExecutionRuleId(long executeRuleId);

    List<Long> selectCampaignIdListOrderBySupportCount(@Param("offset") long offset, @Param("size") int size);
}
