package com.adinstar.pangyo.mapper;

import com.adinstar.pangyo.model.CampaignCandidate;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface CampaignCandidateMapper {
    List<CampaignCandidate> selectListByStarIdAndExecuteRuleId(@Param("starId") long starId, @Param("executeRuleId") long executeRuleId, @Param("offset") int offset, @Param("size") int size);

    CampaignCandidate selectByStarIdAndId(@Param("starId") long starId, @Param("id") long id);

    void insert(CampaignCandidate campaignCandidate);

    void update(CampaignCandidate campaignCandidate);

    void updatePollCount(@Param("starId") long starId, @Param("id") long id, @Param("delta") int delta);
}
