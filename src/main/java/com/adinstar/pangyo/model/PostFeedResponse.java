package com.adinstar.pangyo.model;

import lombok.Data;

import java.util.List;

@Data
public class PostFeedResponse extends FeedResponse {
    private List<Long> likeList;

    public PostFeedResponse(List<Post> postList, int expactListSize, List<Long> likeList) {
        super(postList, expactListSize);
        this.likeList = likeList;
    }
}
