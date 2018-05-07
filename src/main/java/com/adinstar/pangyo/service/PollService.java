package com.adinstar.pangyo.service;

import com.adinstar.pangyo.constant.PangyoEnum;
import com.adinstar.pangyo.constant.PangyoErrorMessage;
import com.adinstar.pangyo.controller.exception.NotFoundException;
import com.adinstar.pangyo.mapper.ActionHistoryMapper;
import com.adinstar.pangyo.model.ActionHistory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;


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

    public List<ActionHistory> getList(PangyoEnum.ContentType contentType, List<Long> contentIds, long userId) {
        return actionHistoryMapper.selectListByActionTypeAndContentTypeAndContentIdsAndUserId(
                PangyoEnum.ActionType.POLL, contentType, contentIds, userId);
    }

    public List<Long> getContentIdList (PangyoEnum.ContentType contentType, List<Long> contentIds, long userId) {
        return getList(contentType, contentIds, userId).stream()
                .map(ActionHistory::getContentId)
                .collect(Collectors.toList());
    }

    public boolean isPolled (PangyoEnum.ContentType contentType, long contentId, long userId) {
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
