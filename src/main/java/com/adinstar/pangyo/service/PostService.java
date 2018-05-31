package com.adinstar.pangyo.service;

import com.adinstar.pangyo.constant.PangyoEnum;
import com.adinstar.pangyo.mapper.PostMapper;
import com.adinstar.pangyo.model.FeedResponse;
import com.adinstar.pangyo.model.Post;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PostService {

    @Autowired
    private PostMapper postMapper;

    private static final int LIST_SIZE = 10;

    public FeedResponse<Post> getListByStarId(long starId, Optional lastId) {
        return new FeedResponse<>(
                postMapper.selectListByStarId(starId, (long)lastId.orElse(Long.MAX_VALUE), LIST_SIZE+1),
                LIST_SIZE
        );
    }

    public Post getById(long id) {
        return postMapper.selectById(id);
    }

    public void add(Post post) {
        postMapper.insert(post);
    }

    public void modify(Post post) {
        postMapper.update(post);
    }

    public void remove(long id) {
        postMapper.updateStatus(id, PangyoEnum.PostStatus.DELETED);
    }

    public void updateLikeCount(long id, int delta) {
        postMapper.updateLikeCount(id, delta);
    }

    public void updateViewCount(long id, int delta) {
        postMapper.updateViewCount(id, delta);
    }

    public void updateCommentCount(long id, int delta) {
        postMapper.updateCommentCount(id, delta);
    }
}
