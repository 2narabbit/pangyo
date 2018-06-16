package com.adinstar.pangyo.service;

import com.adinstar.pangyo.constant.PangyoEnum;
import com.adinstar.pangyo.controller.exception.BadRequestException;
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

    public String getUniqueKeyPostfix(CampaignCandidate campaignCandidate, long userId) {
        return String.format("%s_%s_%s", campaignCandidate.getExecuteRuleId(), campaignCandidate.getStar().getId(), userId);
    }

    @Transactional
    public void add(PangyoEnum.ContentType contentType, long contentId, long userId) {
        if (PangyoEnum.ContentType.CANDIDATE.equals(contentType)) {
            CampaignCandidate campaignCandidate = campaignCandidateService.getById(contentId);
            if (campaignCandidate == null){
                throw NotFoundException.CAMPAIGN_CANDIDATE;
            }

            String keyPostfix = getUniqueKeyPostfix(campaignCandidate, userId);
            if (!checkUnique(contentType, keyPostfix)) {
                throw BadRequestException.DUPLICATE_POLL;
            }

            campaignCandidateService.updatePollCount(contentId, 1);
            super.addUnique(contentType, keyPostfix);
        }

        super.add(contentType, contentId, userId);
    }

    @Transactional
    public void remove(PangyoEnum.ContentType contentType, long contentId, long userId) {
        super.remove(contentType, contentId, userId);

        if (PangyoEnum.ContentType.CANDIDATE.equals(contentType)) {
            removeUnique(contentType, getUniqueKeyPostfix(campaignCandidateService.getById(contentId), userId));
            campaignCandidateService.updatePollCount(contentId, -1);
        }
    }
}
