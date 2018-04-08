package com.adinstar.pangyo.admin.service;

import com.adinstar.pangyo.exception.InvalidSituation;
import com.adinstar.pangyo.mapper.CampaignRankMapper;
import com.adinstar.pangyo.mapper.PolicyMapper;
import com.adinstar.pangyo.model.Campaign;
import com.adinstar.pangyo.model.Policy;
import com.adinstar.pangyo.service.CampaignService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
public class Ranker {

    private static final String CAMPAIGN_SNAPSHOT_KEY = "CAMPAIGN_SNAPSHOT_TERM";

    public static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH");

    @Autowired
    private PolicyMapper policyMapper;

    @Autowired
    private CampaignRankMapper campaignRankMapper;

    @Autowired
    private CampaignService campaignService;

    public void snapshotForCampaign() {
        long hours = getPolicyByCampaignSnapshotTerm();

        LocalDateTime now = LocalDateTime.now();
        LocalDateTime lastTime = LocalDateTime.parse(campaignRankMapper.selectLastTime(), formatter);
        if (lastTime != null && now.isAfter(lastTime.plusHours(hours))) {
            return;
        }

        String formatDateTime = now.format(formatter) + ":00:00";
        List<Campaign> campaignList = campaignService.getCampaignList(campaignService.getRunningExecuteRuleId());
        for (int i = 0; i < campaignList.size(); i++) {
            campaignRankMapper.insertCampaignRank(campaignList.get(i).getId(), formatDateTime, i + 1);
        }
    }

    private long getPolicyByCampaignSnapshotTerm() {
        Policy policy = policyMapper.selectPolicyByKey(CAMPAIGN_SNAPSHOT_KEY);
        if (policy == null) {
            throw InvalidSituation.NOT_FOUND_POLICY;
        }

        return Long.valueOf(policy.getValue());
    }
}
