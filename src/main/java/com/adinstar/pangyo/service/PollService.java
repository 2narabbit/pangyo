package com.adinstar.pangyo.service;

import com.adinstar.pangyo.constant.PangyoEnum;
import com.adinstar.pangyo.controller.exception.NotFoundException;
import com.adinstar.pangyo.model.CampaignCandidate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
public class PollService extends ActionService {

    @Autowired
    private CampaignCandidateService campaignCandidateService;

    public PollService() {
        super(PangyoEnum.ActionType.POLL);
    }

    @Transactional
    public void add(PangyoEnum.ContentType contentType, long contentId, long userId) {
        if (PangyoEnum.ContentType.CANDIDATE.equals(contentType)) {
            CampaignCandidate campaignCandidate = campaignCandidateService.getById(contentId);
            if (campaignCandidate == null){
                throw NotFoundException.CAMPAIGN_CANDIDATE;
            }
            campaignCandidateService.updatePollCount(contentId, 1);
        }

        super.add(contentType, contentId, userId);
    }

    @Transactional
    public void remove(PangyoEnum.ContentType contentType, long contentId, long userId) {
        super.remove(contentType, contentId, userId);

        if (PangyoEnum.ContentType.CANDIDATE.equals(contentType)) {
            campaignCandidateService.updatePollCount(contentId, -1);
        }
    }
}
