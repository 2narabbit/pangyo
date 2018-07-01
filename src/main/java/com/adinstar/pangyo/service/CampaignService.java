package com.adinstar.pangyo.service;

import com.adinstar.pangyo.admin.service.PolicyService;
import com.adinstar.pangyo.constant.PangyoEnum;
import com.adinstar.pangyo.mapper.CampaignMapper;
import com.adinstar.pangyo.model.Campaign;
import com.adinstar.pangyo.model.Policy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.json.BasicJsonParser;
import org.springframework.boot.json.JsonParser;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class CampaignService {

    @Autowired
    private CampaignMapper campaignMapper;

    @Autowired
    private ViewCounter viewCounter;

    @Autowired
    private PolicyService policyService;

    public List<Long> getCampaignIdListOrderBySupportCount(long offset, int size) {
        return campaignMapper.selectCampaignIdListOrderBySupportCount(offset, size);
    }

    public Campaign getById(long id) {
        return campaignMapper.selectById(id);
    }

    public void updateViewCount(long id, int delta) {
        viewCounter.updateViewCount(id, delta, (t, u) -> campaignMapper.updateViewCount(t, u));
    }

    public String getRankBenefitsOfCampaign(Campaign campaign) {
        String ranking = campaignMapper.selectRankingByIdAndExecuteRuleId(campaign.getId(), campaign.getExecuteRuleId());

        // benefit을 어떻게 관리 할 지... 허허-
        Policy policy = policyService.getPolicyValueByKey(PangyoEnum.PolicyKey.CAMPAIGN_BENEFITS);

        JsonParser jsonParser = new BasicJsonParser();
        Map<String, Object> benefits = jsonParser.parseMap(policy.getValue());
        if (benefits.containsKey(ranking)) {
            return String.valueOf(benefits.get(ranking));
        }

        return null;
    }
}
