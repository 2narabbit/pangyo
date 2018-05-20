package com.adinstar.pangyo.service;

import com.adinstar.pangyo.constant.PangyoEnum;
import com.adinstar.pangyo.mapper.CommentMapper;
import com.adinstar.pangyo.model.Comment;
import com.adinstar.pangyo.model.FeedResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class CommentService {

    @Autowired
    private CommentMapper commentMapper;

    @Autowired
    private PostService postService;

    private static final int LIST_SIZE = 5;

    public FeedResponse<Comment> getList(PangyoEnum.ContentType contentType, long contentId, Optional lastId) {
        List<Comment> postList = commentMapper.selectList(contentType, contentId, (long)lastId.orElse(Long.MAX_VALUE), LIST_SIZE+1);
        return new FeedResponse<>(postList, LIST_SIZE);
    }

    // TODO: 로그인체크
    @Transactional
    public void add(Comment comment) {
        commentMapper.insert(comment);
        if (PangyoEnum.ContentType.POST.equals(comment.getContentType())) {
            postService.updateCommentCount(comment.getContentId(), 1);
        }
    }

    // TODO: 로그인체크, 유저권한 체크
    public void modify(Comment comment) {
        commentMapper.update(comment);
    }

    // TODO: 로그인체크, 유저권한 체크
    @Transactional
    public void remove(long id) {
        Comment comment = commentMapper.selectById(id);

        commentMapper.updateStatus(id, PangyoEnum.CommentStatus.DELETED);
        if (PangyoEnum.ContentType.POST.equals(comment.getContentType())) {
            postService.updateCommentCount(comment.getContentId(), -1);
        }
    }
}
