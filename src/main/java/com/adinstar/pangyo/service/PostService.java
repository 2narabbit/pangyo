package com.adinstar.pangyo.service;

import com.adinstar.pangyo.mapper.PostMapper;
import com.adinstar.pangyo.model.FeedResponse;
import com.adinstar.pangyo.model.Post;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PostService {

    @Autowired
    private PostMapper postMapper;

    private static final int LIST_SIZE = 10;

    private long getLastId(Long lastId) {
        return lastId == null ? Long.MAX_VALUE : lastId;
    }

    private FeedResponse<Post> getResponse(List<Post> postList) {
        FeedResponse<Post> feedResponse = new FeedResponse();
        if (postList.size() > LIST_SIZE) {
            postList = postList.subList(0, postList.size()-1);
            feedResponse.setHasMore(true);
        } else {
            feedResponse.setHasMore(false);
        }
        feedResponse.setList(postList);
        feedResponse.setLastId(postList.get(postList.size()-1).getId());

        return feedResponse;
    }

    public FeedResponse<Post> findAll(Long lastId) {
        return getResponse(postMapper.selectList(getLastId(lastId), LIST_SIZE+1));
    }

    public FeedResponse<Post> findAllByStarId(long starId, Long lastId) {
        return getResponse(postMapper.selectListByStarId(starId, getLastId(lastId), LIST_SIZE));
    }

    public Post findById(long id) {
        return postMapper.selectById(id);
    }

    public long add(Post post) {
        postMapper.insert(post);
        return post.getId();
    }

    public long modify(Post post) {
        // TODO : user 권한 체크
        postMapper.update(post);
        return post.getId();
    }

    public void remove(Integer postId) {
        // TODO : user 권한 체크
        postMapper.delete(postId);
    }
}
