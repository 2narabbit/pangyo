package com.adinstar.pangyo.service;

import com.adinstar.pangyo.constant.PangyoEnum;
import com.adinstar.pangyo.constant.PangyoEnum.ExecutionRuleStatus;
import com.adinstar.pangyo.constant.PangyoEnum.ExecutionRuleType;
import com.adinstar.pangyo.controller.exception.BadRequestException;
import com.adinstar.pangyo.mapper.CampaignCandidateMapper;
import com.adinstar.pangyo.mapper.ExecutionRuleMapper;
import com.adinstar.pangyo.model.CampaignCandidate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CampaignCandidateService {
    private static final int LIST_SIZE = 20;

    @Autowired
    private CampaignCandidateMapper campaignCandidateMapper;

    @Autowired
    private ExecutionRuleMapper executionRuleMapper;

    // ASK : select 결과가 null 일 경우를 별도 처리하는게 좋을까? 존재해서는 안 될 상황으로 판단해서 지금처럼 두고 500 ERROR를 뱉을까??;
    public long getExecuteRuleId() {
//        executionRuleMapper.selectRuleId(ExecutionRuleType.CANDIDATE, ExecutionRuleStatus.RUNNING);
        return 1L; // ASK 이후에 처리하도록 하자!!
    }

    public List<CampaignCandidate> getRecentTurnList(long startId, Optional<Integer> opPage, Optional<Integer> opSize) {
        int size = opSize.orElse(LIST_SIZE);
        int offset = (opPage.orElse(1) - 1) * size;
        return campaignCandidateMapper.selectListByStarId(startId, getExecuteRuleId(), offset, size);
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
        CampaignCandidate campaignCandidate = new CampaignCandidate();
        campaignCandidate.setId(id);
        campaignCandidate.setStatus(PangyoEnum.CampaignCandidateStatus.DELETED);
        modify(campaignCandidate);
    }
}
