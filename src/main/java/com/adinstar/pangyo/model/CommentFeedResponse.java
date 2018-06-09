package com.adinstar.pangyo.model;

import lombok.Data;

import java.util.List;

@Data
public class CommentFeedResponse extends FeedResponse {
    private List<Long> myList;

    public CommentFeedResponse(List<Comment> commentList, int expactListSize, List<Long> myList) {
        super(commentList, expactListSize);
        this.myList = myList;
    }
}
