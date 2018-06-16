package com.adinstar.pangyo.service;

import com.adinstar.pangyo.constant.PangyoEnum;
import com.adinstar.pangyo.controller.exception.NotFoundException;
import com.adinstar.pangyo.mapper.ActionHistoryMapper;
import com.adinstar.pangyo.model.ActionHistory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.stream.Collectors;

public class ActionService {

    @Autowired
    private ActionHistoryMapper actionHistoryMapper;

    private PangyoEnum.ActionType actionType;

    public ActionService(PangyoEnum.ActionType actionType) {
        this.actionType = actionType;
    }

    public void add(PangyoEnum.ContentType contentType, long contentId, long userId) {
        ActionHistory actionHistory = new ActionHistory();
        actionHistory.setActionType(actionType);
        actionHistory.setContentType(contentType);
        actionHistory.setContentId(contentId);
        actionHistory.setUserId(userId);

        actionHistoryMapper.insert(actionHistory);
    }

    public ActionHistory get(PangyoEnum.ContentType contentType, long contentId, long userId) {
        return actionHistoryMapper.selectByActionTypeAndContentTypeAndContentIdAndUserId(
                actionType, contentType, contentId, userId);
    }

    public List<ActionHistory> getList(PangyoEnum.ContentType contentType, List<Long> contentIds, long userId) {
        return actionHistoryMapper.selectListByActionTypeAndContentTypeAndContentIdsAndUserId(
                actionType, contentType, contentIds, userId);
    }

    public List<Long> getContentIdList (PangyoEnum.ContentType contentType, List<Long> contentIds, long userId) {
        return getList(contentType, contentIds, userId).stream()
                .map(ActionHistory::getContentId)
                .collect(Collectors.toList());
    }

    public boolean isActioned (PangyoEnum.ContentType contentType, long contentId, long userId) {
        return (get(contentType, contentId, userId) == null) ? false : true;
    }

    public void remove(PangyoEnum.ContentType contentType, long contentId, long userId) {
        int rc = actionHistoryMapper.deleteByActionTypeAndContentTypeAndContentIdAndUserId(
                actionType, contentType, contentId, userId);
        if (rc == 0) {
            throw NotFoundException.ACTION_HISTORY;
        }
    }

    /**************************************************************************************
     *
     * Action 중복체크를 위한 메소드
     *
     **************************************************************************************/

    public String getUniqueKey(PangyoEnum.ContentType contentType, String postfix) {
        return String.format("%s_%s_%s", actionType, contentType, postfix);
    }

    public boolean checkUnique(PangyoEnum.ContentType contentType, String keyPostfix) {
        return actionHistoryMapper.selectFromActionUnique(getUniqueKey(contentType, keyPostfix)) == null;
    }

    public void addUnique(PangyoEnum.ContentType contentType, String keyPostfix) {
        actionHistoryMapper.insertIntoActionUnique(getUniqueKey(contentType, keyPostfix));
    }

    public void removeUnique(PangyoEnum.ContentType contentType, String keyPostfix) {
        actionHistoryMapper.deleteFromActionUnique(getUniqueKey(contentType, keyPostfix));
    }
}
