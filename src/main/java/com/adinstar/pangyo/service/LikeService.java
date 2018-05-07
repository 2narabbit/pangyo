package com.adinstar.pangyo.service;

import com.adinstar.pangyo.constant.PangyoEnum;
import com.adinstar.pangyo.mapper.ActionHistoryMapper;
import com.adinstar.pangyo.model.ActionHistory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
public class LikeService {

    @Autowired
    private ActionHistoryMapper actionHistoryMapper;

    @Autowired
    private PostService postService;

    @Transactional
    public void add(PangyoEnum.ContentType contentType, long contentId, long userId) {
        actionHistoryMapper.insert(ActionHistory.builder()
                .actionType(PangyoEnum.ActionType.LIKE)
                .contentType(contentType)
                .contentId(contentId)
                .userId(userId)
                .build());

        if (PangyoEnum.ContentType.POST.equals(contentType)) {
            postService.updateLikeCount(contentId, 1);
        }
    }

    public ActionHistory get(PangyoEnum.ContentType contentType, long contentId, long userId) {
        return actionHistoryMapper.selectByActionTypeAndContentTypeAndContentIdAndUserId(
                PangyoEnum.ActionType.LIKE, contentType, contentId, userId
        );
    }

    public boolean doLike (PangyoEnum.ContentType contentType, long contentId, long userId) {
        return get(contentType, contentId, userId) != null;
    }

    @Transactional
    public void remove(PangyoEnum.ContentType contentType, long contentId, long userId) {
        actionHistoryMapper.deleteByActionTypeAndContentTypeAndContentIdAndUserId(
                PangyoEnum.ActionType.LIKE, contentType, contentId, userId
        );

        if (PangyoEnum.ContentType.POST.equals(contentType)) {
            postService.updateLikeCount(contentId, -1);
        }
    }
}
