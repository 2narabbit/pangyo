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

    private static final int LIST_SIZE = 5;

    public FeedResponse<Comment> getList(PangyoEnum.ContentType contentType, long contentId, Optional lastId) {
        List<Comment> postList = commentMapper.selectList(contentType, contentId, (long)lastId.orElse(Long.MAX_VALUE), LIST_SIZE+1);
        return new FeedResponse<>(postList, LIST_SIZE);
    }

    public long getCount(PangyoEnum.ContentType contentType, long contentId) {
        Long count = commentMapper.selectCount(contentType, contentId);
        if (count == null)  {
            count = 0L;
        }

        return count;
    }

    // TODO: 로그인체크
    @Transactional
    public void add(Comment comment) {
        commentMapper.insert(comment);
        int rc = commentMapper.updateCount(comment.getContentType(), comment.getContentId(), 1);
        if (rc == 0) {
            commentMapper.insertCount(comment.getContentType(), comment.getContentId());
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
        commentMapper.updateCount(comment.getContentType(), comment.getContentId(), -1);
    }

    public void removeMeta(PangyoEnum.ContentType contentType, long contentId) {
        commentMapper.updateMetaStatus(contentType, contentId, PangyoEnum.CommentStatus.DELETED);
    }
}
