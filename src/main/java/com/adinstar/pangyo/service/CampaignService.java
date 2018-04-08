package com.adinstar.pangyo.service;

import com.adinstar.pangyo.mapper.CampaignMapper;
import com.adinstar.pangyo.model.Campaign;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CampaignService {

    @Autowired
    private CampaignMapper campaignMapper;

    @Autowired
    private CampaignCandidateService campaignCandidateService;

    public long getRunningExecuteRuleId() {
        return campaignCandidateService.getRunningExecuteRuleId();
    }

    public List<Campaign> getCampaignList(long executeRuleId) {
        return campaignMapper.selectCampaignListByExecutionRuleId(executeRuleId);
    }
}
