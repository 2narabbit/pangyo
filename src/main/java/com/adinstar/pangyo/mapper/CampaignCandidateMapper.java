package com.adinstar.pangyo.mapper;

import com.adinstar.pangyo.constant.PangyoEnum.CampaignCandidateStatus;
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

    void updateStatus(@Param("starId") long starId, @Param("id") long id, @Param("status") CampaignCandidateStatus deleted);

    void updatePollCount(@Param("starId") long starId, @Param("id") long id, @Param("delta") int delta);

    void updateViewCount(@Param("starId") long starId, @Param("id") long id, @Param("delta") int delta);

    CampaignCandidate selectByStarIdAndUserIdAndExecuteRuleId(@Param("starId") long starId, @Param("userId") long userId, @Param("executeRuleId") long executeRuleId);
}
