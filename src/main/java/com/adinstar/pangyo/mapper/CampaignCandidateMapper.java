package com.adinstar.pangyo.mapper;

import com.adinstar.pangyo.constant.PangyoEnum.CampaignCandidateStatus;
import com.adinstar.pangyo.model.CampaignCandidate;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface CampaignCandidateMapper {
    List<CampaignCandidate> selectListByStarIdAndExecuteRuleId(@Param("starId") long starId, @Param("executeRuleId") long executeRuleId, @Param("offset") int offset, @Param("size") int size);

    CampaignCandidate selectById(@Param("id") long id);

    void insert(CampaignCandidate campaignCandidate);

    void update(CampaignCandidate campaignCandidate);

    void updateStatus(@Param("id") long id, @Param("status") CampaignCandidateStatus deleted);

    void updatePollCount(@Param("id") long id, @Param("delta") int delta);

    List<CampaignCandidate> selectListByStarIdAndUserIdAndExecuteRuleId(@Param("starId") long starId, @Param("userId") long userId, @Param("executeRuleId") long executeRuleId);
}
