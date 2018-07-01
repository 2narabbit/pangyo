package com.adinstar.pangyo.service;

import com.adinstar.pangyo.constant.PangyoEnum;
import com.adinstar.pangyo.mapper.PostMapper;
import com.adinstar.pangyo.model.Post;
import com.adinstar.pangyo.model.PostFeedResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.function.BiFunction;

@Service
public class PostService {

    @Autowired
    private PostMapper postMapper;

    @Autowired
    private LikeService likeService;

    @Autowired
    private ViewCounter viewCounter;

    private static final int LIST_SIZE = 10;

    public PostFeedResponse getListByStarId(long starId, Optional lastId, Long userId) {
        List<Post> postList =  postMapper.selectListByStarId(starId, (long)lastId.orElse(Long.MAX_VALUE), LIST_SIZE + 1);

        List<Long> likeList;
        if (userId != null) {
            List<Long> ids = postList.stream()
                    .map(Post::getId)
                    .collect(Collectors.toList());
            likeList = likeService.getContentIdList(PangyoEnum.ContentType.POST, ids, userId);
        } else {
            likeList = new ArrayList<>();
        }

        return new PostFeedResponse(postList, LIST_SIZE, likeList);
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
        viewCounter.updateViewCount(id, delta, (t, u) -> postMapper.updateViewCount(t, u));
    }

    public void updateCommentCount(long id, int delta) {
        postMapper.updateCommentCount(id, delta);
    }
}
