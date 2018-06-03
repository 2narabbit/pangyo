package com.adinstar.pangyo.service;

import com.adinstar.pangyo.constant.PangyoEnum;
import com.adinstar.pangyo.controller.exception.NotFoundException;
import com.adinstar.pangyo.model.Post;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
public class LikeService extends ActionService {

    @Autowired
    private PostService postService;

    public LikeService() {
        super(PangyoEnum.ActionType.LIKE);
    }

    @Transactional
    public void add(PangyoEnum.ContentType contentType, long contentId, long userId) {
        if (PangyoEnum.ContentType.POST.equals(contentType)) {
            Post post = postService.getById(contentId);
            if (post == null){
                throw NotFoundException.POST;
            }
            postService.updateLikeCount(contentId, 1);
        }

        super.add(contentType, contentId, userId);
    }

    @Transactional
    public void remove(PangyoEnum.ContentType contentType, long contentId, long userId) {
        super.remove(contentType, contentId, userId);

        if (PangyoEnum.ContentType.POST.equals(contentType)) {
            postService.updateLikeCount(contentId, -1);
        }
    }
}
