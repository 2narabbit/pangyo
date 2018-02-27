package com.adinstar.pangyo.service;

import com.adinstar.pangyo.mapper.PostMapper;
import com.adinstar.pangyo.model.Post;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PostService {

    @Autowired
    private PostMapper postMapper;

    private static final int LIST_SIZE = 20;

    private long getLastId(Long lastId) {
        return lastId == null ? Long.MAX_VALUE : lastId;
    }

    public List<Post> findAll(Long lastId) {
        return postMapper.selectList(getLastId(lastId), LIST_SIZE);
    }

    public List<Post> findAllByStarId(long starId, Long lastId) {
        return postMapper.selectListByStarId(starId, getLastId(lastId), LIST_SIZE);
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
