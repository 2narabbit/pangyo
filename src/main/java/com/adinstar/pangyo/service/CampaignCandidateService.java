package com.adinstar.pangyo.service;

import com.adinstar.pangyo.constant.PangyoEnum;
import com.adinstar.pangyo.constant.PangyoEnum.CampaignCandidateStatus;
import com.adinstar.pangyo.constant.PangyoEnum.ExecutionRuleType;
import com.adinstar.pangyo.mapper.CampaignCandidateMapper;
import com.adinstar.pangyo.model.CampaignCandidate;
import com.adinstar.pangyo.model.CampaignCandidateFeedResponse;
import com.adinstar.pangyo.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CampaignCandidateService {
    private static final int DEFAULT_PAGE = 1;
    private static final int LIST_SIZE = 20;

    @Autowired
    private CampaignCandidateMapper campaignCandidateMapper;

    @Autowired
    private ExecutionRuleService executionRuleService;

    @Autowired
    private PollService pollService;

    public long getRunningExecuteRuleId() {
        return executionRuleService.getProgressExecuteRuleIdByType(ExecutionRuleType.CANDIDATE);
    }

    public CampaignCandidateFeedResponse getRunningList(long starId, Optional<Integer> opPage, Optional<Integer> opSize, Long userId) {
        int size = opSize.orElse(LIST_SIZE);
        int offset = (opPage.orElse(DEFAULT_PAGE) - DEFAULT_PAGE) * size;

        List<CampaignCandidate> campaignCandidateList = campaignCandidateMapper.selectListByStarIdAndExecuteRuleId(starId, getRunningExecuteRuleId(), offset, size+1);
        List<Long> pollList;
        if (userId != null) {
            List<Long> ids = campaignCandidateList.stream()
                    .map(CampaignCandidate::getId)
                    .collect(Collectors.toList());
            pollList = pollService.getContentIdList(PangyoEnum.ContentType.CANDIDATE, ids, userId);
        } else {
            pollList = new ArrayList<>();
        }

        return new CampaignCandidateFeedResponse(campaignCandidateList, opPage.orElse(DEFAULT_PAGE), size, pollList);
    }

    public CampaignCandidate getById(long id) {
        return campaignCandidateMapper.selectById(id);
    }

    public void add(CampaignCandidate campaignCandidate) {
        campaignCandidate.setExecuteRuleId(getRunningExecuteRuleId());
        campaignCandidateMapper.insert(campaignCandidate);
    }

    public void modify(CampaignCandidate campaignCandidate) {
        campaignCandidateMapper.update(campaignCandidate);
    }

    public void remove(long id) {
        campaignCandidateMapper.updateStatus(id, CampaignCandidateStatus.DELETED);
    }

    public void updatePollCount(long id, int delta) {
        campaignCandidateMapper.updatePollCount(id, delta);
    }

    public CampaignCandidate getRunningCandidateByStarIdAndUserId(long starId, long userId) {
        return campaignCandidateMapper.selectByStarIdAndUserIdAndExecuteRuleId(starId, userId, getRunningExecuteRuleId());
    }
}
