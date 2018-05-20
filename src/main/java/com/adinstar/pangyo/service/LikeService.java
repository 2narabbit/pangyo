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
public class LikeService extends ActionService {

    @Autowired
    private PostService postService;

    public LikeService(ActionHistoryMapper actionHistoryMapper) {
        super(actionHistoryMapper, PangyoEnum.ActionType.LIKE);
    }

    @Transactional
    public void add(PangyoEnum.ContentType contentType, long contentId, long userId) {
        super.add(contentType, contentId, userId);

        if (PangyoEnum.ContentType.POST.equals(contentType)) {
            postService.updateLikeCount(contentId, 1);
        }
    }

    @Transactional
    public void remove(PangyoEnum.ContentType contentType, long contentId, long userId) {
        super.remove(contentType, contentId, userId);

        if (PangyoEnum.ContentType.POST.equals(contentType)) {
            postService.updateLikeCount(contentId, -1);
        }
    }
}
