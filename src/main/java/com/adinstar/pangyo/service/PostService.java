package com.adinstar.pangyo.service;

import com.adinstar.pangyo.common.annotation.CheckAuthority;
import com.adinstar.pangyo.common.annotation.HintKey;
import com.adinstar.pangyo.constant.PangyoEnum;
import com.adinstar.pangyo.mapper.PostMapper;
import com.adinstar.pangyo.model.FeedResponse;
import com.adinstar.pangyo.model.Post;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static com.adinstar.pangyo.common.annotation.HintKey.POST;
import static com.adinstar.pangyo.common.annotation.HintKey.POST_ID;
import static com.adinstar.pangyo.common.annotation.HintKey.STAR_ID;

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

    public Post getByStarIdAndId(long starId, long id) {
        return postMapper.selectByStarIdAndId(starId, id);
    }

    @CheckAuthority(type = Post.class, checkType = PangyoEnum.CheckingType.OBJECT, isCheckOwner = false)
    public void add(@HintKey(STAR_ID) long starId, @HintKey(POST) Post post) {
        postMapper.insert(post);
    }

    @CheckAuthority(type = Post.class, checkType = PangyoEnum.CheckingType.OBJECT)
    public void modify(@HintKey(STAR_ID) long starId, @HintKey(POST) Post post) {
        postMapper.update(post);
    }

    @CheckAuthority(type = Post.class, checkType = PangyoEnum.CheckingType.ID)
    public void remove(@HintKey(STAR_ID) long starId, @HintKey(POST_ID) long id) {
        postMapper.updateStatus(starId, id, PangyoEnum.PostStatus.DELETED);
    }

    public void updateViewCount(long id, int delta) {
        postMapper.updateViewCount(id, delta);
    }

    public void updateCommentCount(long id, int delta) {
        postMapper.updateCommentCount(id, delta);
    }
}
