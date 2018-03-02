package com.adinstar.pangyo.mapper;

import com.adinstar.pangyo.model.CampaignCandidate;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface CampaignCandidateMapper {
    List<CampaignCandidate> selectListByStarId(@Param("starId") long starId, @Param("executeRuleId") long executeRuleId, @Param("offset") int offset, @Param("size") int size);

    CampaignCandidate selectById(long id);

    void insert(CampaignCandidate campaignCandidate);

    void update(CampaignCandidate campaignCandidate);

    void delete(long id);
}
