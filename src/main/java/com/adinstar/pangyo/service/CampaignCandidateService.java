package com.adinstar.pangyo.service;

import com.adinstar.pangyo.constant.PangyoEnum;
import com.adinstar.pangyo.constant.PangyoEnum.CampaignCandidateStatus;
import com.adinstar.pangyo.constant.PangyoEnum.ExecutionRuleType;
import com.adinstar.pangyo.mapper.CampaignCandidateMapper;
import com.adinstar.pangyo.model.CampaignCandidate;
import com.adinstar.pangyo.model.CampaignCandidateFeedResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
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

        List<CampaignCandidate> campaignCandidateList = campaignCandidateMapper.selectListByStarIdAndExecuteRuleId(starId, getRunningExecuteRuleId(), offset, size + 1);
        List<Long> pollList;
        if (userId != null && !campaignCandidateList.isEmpty()) {
            List<Long> ids = campaignCandidateList.stream()
                    .map(CampaignCandidate::getId)
                    .collect(Collectors.toList());
            pollList = pollService.getContentIdList(PangyoEnum.ContentType.CANDIDATE, ids, userId);
        } else {
            pollList = Collections.EMPTY_LIST;
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

    // checked! 근데 중복 요청이 들어오면 막을 수 있는 방법이 없음.
    // 이런 경우는 db 로 유니크 키 생성해야 할 것 같은데ㅠ 요구 사항이 달라지면 db alter 치나??
    // 명시적으로 유효한 lock을 잡아 두는 방법에 대해서도 고민해야하나? 우선 일반적인 경우...는 걍 이러걸로 커버 되는데ㅠ
    private List<CampaignCandidate> getRunningCandidateByStarIdAndUserId(long starId, long userId) {
        return campaignCandidateMapper.selectListByStarIdAndUserIdAndExecuteRuleId(starId, userId, getRunningExecuteRuleId());
    }

    public boolean isAlreadyCandidateRegistration(long starId, Long uerId) {
        if (uerId == null) {
            return false;
        }

        return getRunningCandidateByStarIdAndUserId(starId, uerId).isEmpty() ? false : true;
    }
}
