package com.adinstar.pangyo.service;

import com.adinstar.pangyo.constant.PangyoEnum;
import com.adinstar.pangyo.mapper.ActionHistoryMapper;
import com.adinstar.pangyo.model.ActionHistory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
public class PollService extends ActionService {

    @Autowired
    private CampaignCandidateService campaignCandidateService;

    public PollService(ActionHistoryMapper actionHistoryMapper) {
        super(actionHistoryMapper, PangyoEnum.ActionType.POLL);
    }

    @Transactional
    public void add(PangyoEnum.ContentType contentType, long contentId, long userId) {
        super.add(contentType, contentId, userId);

        if (PangyoEnum.ContentType.CANDIDATE.equals(contentType)) {
            campaignCandidateService.updatePollCount(contentId, 1);
        }
    }

    @Transactional
    public void remove(PangyoEnum.ContentType contentType, long contentId, long userId) {
        super.remove(contentType, contentId, userId);

        if (PangyoEnum.ContentType.CANDIDATE.equals(contentType)) {
            campaignCandidateService.updatePollCount(contentId, -1);
        }
    }
}
