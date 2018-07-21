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
import java.util.stream.Collectors;
import java.util.stream.IntStream;

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
        List<Campaign> campaignList = campaignMapper.selectListByExecuteRuleId(getRunningExecuteRuleId(), rankId - 1, size + 1);

        LocalDateTime updateTime = LocalDateTime.now();
        List<RankData<Campaign>> rankDataList = IntStream.range(0, campaignList.size())
                                                         .mapToObj(i -> new RankData<Campaign>(Long.valueOf(i) + 1L, campaignList.get(i), updateTime))
                                                         .collect(Collectors.toList());
        return new FeedResponse<>(rankDataList, size);
    }

    public String getCampaignRankBenefits() {
        return policyService.getPolicyValueByKey(PangyoEnum.PolicyKey.CAMPAIGN_RANK_BENEFITS).getValue();
    }
}