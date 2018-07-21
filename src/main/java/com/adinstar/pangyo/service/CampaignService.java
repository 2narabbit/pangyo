package com.adinstar.pangyo.service;

import com.adinstar.pangyo.admin.service.PolicyService;
import com.adinstar.pangyo.constant.PangyoEnum;
import com.adinstar.pangyo.mapper.CampaignMapper;
import com.adinstar.pangyo.model.*;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.*;

@Service
public class CampaignService {

    @Autowired
    private CampaignMapper campaignMapper;

    @Autowired
    private ViewCounter viewCounter;

    @Autowired
    private PolicyService policyService;

    @Autowired
    private ExecutionRuleService executionRuleService;

    private long getRunningExecuteRuleId() {
        return executionRuleService.getProgressExecuteRuleIdByType(PangyoEnum.ExecutionRuleType.CAMPAIGN);
    }

    public List<Long> getCampaignIdListOrderBySupportCount(long offset, int size) {
        return campaignMapper.selectCampaignIdListOrderBySupportCount(offset, size);
    }

    public Campaign getById(long id) {
        return campaignMapper.selectById(id);
    }

    public void updateViewCount(long id, int delta) {
        viewCounter.updateViewCount(id, delta, (t, u) -> campaignMapper.updateViewCount(t, u));
    }

    public String getCampaignRankBenefitsByCampaignIdAndExecutionRuleId(long campaignId, long executeRuleId) {
        long ranking = campaignMapper.selectRankingByIdAndExecuteRuleId(campaignId, executeRuleId);

        Policy policy = policyService.getPolicyValueByKey(PangyoEnum.PolicyKey.CAMPAIGN_RANK_BENEFITS);

        ObjectMapper objectMapper = new ObjectMapper();
        try {
            Map<Long, String> benefits = objectMapper.readValue(policy.getValue(), new TypeReference<Map<Long, String>>() {
            });
            if (benefits.containsKey(ranking)) {
                return String.valueOf(benefits.get(ranking));
            }
        } catch (IOException e) {
        }

        return null;
    }

    public FeedResponse<RankData<Campaign>> getRunningList(long rankId, int size) {
        List<Campaign> sortedCampaignList = campaignMapper.selectListByExecuteRuleId(getRunningExecuteRuleId(), rankId - 1, size + 1);

        List<RankData<Campaign>> rankDataList = new ArrayList<>();
        {   // TODO : 실시간으로 할지 말지 여부로 해당 코드 제거하기!
            LocalDateTime updateTime = LocalDateTime.now();
            long ranking = rankId;
            for (Campaign campaign : sortedCampaignList) {
                RankData rankData = new RankData();
                rankData.setId(campaign.getId());
                rankData.setContentId(campaign.getId());
                rankData.setRanking(ranking);
                rankData.setTime(updateTime);
                rankData.setContent(campaign);
                rankData.setDataTime(campaign.getDateTime());
                rankDataList.add(rankData);

                ranking++;
            }
        }

        return new FeedResponse<>(rankDataList, size);
    }

    public String getCampaignRankBenefits() {
        return policyService.getPolicyValueByKey(PangyoEnum.PolicyKey.CAMPAIGN_RANK_BENEFITS).getValue();
    }
}