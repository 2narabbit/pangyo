package com.adinstar.pangyo.mapper;

import com.adinstar.pangyo.model.Campaign;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CampaignMapper {
    List<Campaign> selectCampaignListByExecutionRuleId(long executeRuleId);
}
