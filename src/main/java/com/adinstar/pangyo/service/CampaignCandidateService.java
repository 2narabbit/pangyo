package com.adinstar.pangyo.service;

import com.adinstar.pangyo.constant.PangyoEnum.CampaignCandidateStatus;
import com.adinstar.pangyo.constant.PangyoEnum.ExecutionRuleType;
import com.adinstar.pangyo.controller.exception.UnauthorizedException;
import com.adinstar.pangyo.mapper.CampaignCandidateMapper;
import com.adinstar.pangyo.model.CampaignCandidate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CampaignCandidateService {
    private static final int DEFAULT_PAGE = 1;
    private static final int LIST_SIZE = 20;

    @Autowired
    private CampaignCandidateMapper campaignCandidateMapper;

    @Autowired
    private ExecutionRuleService executionRuleService;

    public long getRunningExecuteRuleId() {
        return executionRuleService.getProgressExecuteRuleIdByType(ExecutionRuleType.CANDIDATE);
    }

    public List<CampaignCandidate> getRunningList(long starId, Optional<Integer> opPage, Optional<Integer> opSize) {
        int size = opSize.orElse(LIST_SIZE);
        int offset = (opPage.orElse(DEFAULT_PAGE) - DEFAULT_PAGE) * size;
        return campaignCandidateMapper.selectListByStarIdAndExecuteRuleId(starId, getRunningExecuteRuleId(), offset, size);
    }

    public CampaignCandidate getById(long id) {
        return campaignCandidateMapper.selectById(id);
    }

    public void add(CampaignCandidate campaignCandidate) {
        try {
            campaignCandidate.setExecuteRuleId(getRunningExecuteRuleId());
            campaignCandidateMapper.insert(campaignCandidate);
        } catch (DuplicateKeyException ex) {
            throw UnauthorizedException.DUPLICATE_CANDIDATE_REGISTER;
        }
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
