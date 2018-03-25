package com.adinstar.pangyo.service;

import com.adinstar.pangyo.common.annotation.CheckAuthority;
import com.adinstar.pangyo.common.annotation.HintKey;
import com.adinstar.pangyo.constant.PangyoEnum;
import com.adinstar.pangyo.mapper.PostMapper;
import com.adinstar.pangyo.model.FeedResponse;
import com.adinstar.pangyo.model.Post;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static com.adinstar.pangyo.common.annotation.HintKey.POST;
import static com.adinstar.pangyo.common.annotation.HintKey.POST_ID;
import static com.adinstar.pangyo.common.annotation.HintKey.STAR_ID;

@Service
public class PostService {

    @Autowired
    private PostMapper postMapper;

    private static final int LIST_SIZE = 10;

    private FeedResponse<Post> getResponse(List<Post> postList) {
        FeedResponse<Post> feedResponse = new FeedResponse();
        if (postList.size() > 0) {
            if (postList.size() > LIST_SIZE) {
                postList = postList.subList(0, postList.size() - 1);
                feedResponse.setHasMore(true);
            }
            feedResponse.setLastId(postList.get(postList.size()-1).getId());
        }

        feedResponse.setList(postList);

        return feedResponse;
    }

    public FeedResponse<Post> getListByStarId(long starId, Optional lastId) {
        return getResponse(postMapper.selectListByStarId(starId, (long)lastId.orElse(Long.MAX_VALUE), LIST_SIZE+1));
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

    @CheckAuthority(type = Post.class, checkType = PangyoEnum.CheckingType.ID, isCheckOwner = false)
    public void increaseViewCount(@HintKey(STAR_ID) long starId, @HintKey(POST_ID) long id, int delta) {
        postMapper.updateViewCount(starId, id, delta);
    }

    @CheckAuthority(type = Post.class, checkType = PangyoEnum.CheckingType.ID)
    public void remove(@HintKey(STAR_ID) long starId, @HintKey(POST_ID) long id) {
        postMapper.updateStatus(starId, id, PangyoEnum.PostStatus.DELETED);
    }
}
