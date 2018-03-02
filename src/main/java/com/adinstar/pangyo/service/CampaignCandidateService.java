package com.adinstar.pangyo.service;

import com.adinstar.pangyo.constant.PangyoEnum.ExecutionRuleStatus;
import com.adinstar.pangyo.constant.PangyoEnum.ExecutionRuleType;
import com.adinstar.pangyo.controller.exception.BadRequestException;
import com.adinstar.pangyo.mapper.CampaignCandidateMapper;
import com.adinstar.pangyo.mapper.ExecutionRuleMapper;
import com.adinstar.pangyo.model.CampaignCandidate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CampaignCandidateService {
    private static final int LIST_SIZE = 20;

    @Autowired
    private CampaignCandidateMapper campaignCandidateMapper;

    @Autowired
    private ExecutionRuleMapper executionRuleMapper;

    public long getExecuteRuleId() {
        return executionRuleMapper.selectRuleId(ExecutionRuleType.CANDIDATE, ExecutionRuleStatus.RUNNING);
    }

    public int getOrElse(Integer checkNumber, int defaultNumer){
        int returnNumber =  (checkNumber == null) ? defaultNumer : checkNumber;
        if (returnNumber < 0) {
            throw BadRequestException.INVALID_PARAM;
        }

        return returnNumber;
    }

    public List<CampaignCandidate> getRecentTurnList(long startId, Integer page, Integer size) {
        int offset = (getOrElse(page, 1) - 1) * getOrElse(size, LIST_SIZE);
        return campaignCandidateMapper.selectListByStarId(startId, getExecuteRuleId(), offset, getOrElse(size, LIST_SIZE));
    }

    public CampaignCandidate getById(long id) {
        return campaignCandidateMapper.selectById(id);
    }

    public void add(CampaignCandidate campaignCandidate) {
        campaignCandidateMapper.insert(campaignCandidate);
    }

    public void modify(CampaignCandidate campaignCandidate) {
        campaignCandidateMapper.update(campaignCandidate);
    }

    public void remove(long id) {
        campaignCandidateMapper.delete(id);
    }
}
