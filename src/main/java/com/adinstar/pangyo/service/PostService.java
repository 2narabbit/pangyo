package com.adinstar.pangyo.service;

import com.adinstar.pangyo.helper.PageHelper;
import com.adinstar.pangyo.mapper.PostMapper;
import com.adinstar.pangyo.model.Post;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PostService {

    @Autowired
    private PostMapper postMapper;

    public List<Post> findAll(PageHelper pageHelper) {
        return postMapper.selectList(pageHelper.getStartOffset(), pageHelper.getEndOffset());
    }

    public List<Post> findAllByStarId(int starId, PageHelper pageHelper) {
        return postMapper.selectListByStarId(starId, pageHelper.getStartOffset(), pageHelper.getEndOffset());
    }

    public Post findById(int id) {
        return postMapper.selectById(id);
    }

    public long save(Post post) {
        postMapper.insert(post);
        return post.getId();
    }

    public void modify(Post post) {
        // TODO : user 권한 체크
        postMapper.update(post);
    }

    public void delete(Integer postId) {
        // TODO : user 권한 체크
        postMapper.delete(postId);
    }
}