package com.adinstar.pangyo.service;

import com.adinstar.pangyo.constant.PangyoEnum;
import com.adinstar.pangyo.constant.PangyoErrorMessage;
import com.adinstar.pangyo.controller.exception.NotFoundException;
import com.adinstar.pangyo.mapper.ActionHistoryMapper;
import com.adinstar.pangyo.model.ActionHistory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
public class PollService {

    @Autowired
    private ActionHistoryMapper actionHistoryMapper;

    @Autowired
    private CampaignCandidateService campaignCandidateService;

    @Transactional
    public void add(PangyoEnum.ContentType contentType, long contentId, long userId) {
        ActionHistory actionHistory = new ActionHistory();
        actionHistory.setActionType(PangyoEnum.ActionType.POLL);
        actionHistory.setContentType(contentType);
        actionHistory.setContentId(contentId);
        actionHistory.setUserId(userId);

        actionHistoryMapper.insert(actionHistory);

        if (PangyoEnum.ContentType.CANDIDATE.equals(contentType)) {
            campaignCandidateService.updatePollCount(contentId, 1);
        }
    }

    public ActionHistory get(PangyoEnum.ContentType contentType, long contentId, long userId) {
        return actionHistoryMapper.selectByActionTypeAndContentTypeAndContentIdAndUserId(
                PangyoEnum.ActionType.POLL, contentType, contentId, userId);
    }

    public boolean doPoll (PangyoEnum.ContentType contentType, long contentId, long userId) {
        return get(contentType, contentId, userId) != null;
    }

    @Transactional
    public void remove(PangyoEnum.ContentType contentType, long contentId, long userId) {
        int rc = actionHistoryMapper.deleteByActionTypeAndContentTypeAndContentIdAndUserId(
                PangyoEnum.ActionType.POLL, contentType, contentId, userId);
        if (rc == 0) {
            throw new NotFoundException(PangyoErrorMessage.NOT_FOUND_ACTION_HISTORY);
        }

        if (PangyoEnum.ContentType.CANDIDATE.equals(contentType)) {
            campaignCandidateService.updatePollCount(contentId, -1);
        }
    }
}
