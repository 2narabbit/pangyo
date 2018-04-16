package com.adinstar.pangyo.service;

import com.adinstar.pangyo.constant.PangyoEnum;
import com.adinstar.pangyo.mapper.CommentMapper;
import com.adinstar.pangyo.model.Comment;
import com.adinstar.pangyo.model.FeedResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CommentService {

    @Autowired
    private CommentMapper commentMapper;

    private static final int LIST_SIZE = 10;

    public FeedResponse<Comment> getList(PangyoEnum.ContentType contentType, long contentId, Optional lastId) {
        List<Comment> postList = commentMapper.selectList(contentType, contentId, (long)lastId.orElse(Long.MAX_VALUE), LIST_SIZE+1);
        return new FeedResponse<>(postList, LIST_SIZE);
    }
}
