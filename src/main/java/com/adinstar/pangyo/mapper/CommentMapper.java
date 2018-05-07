package com.adinstar.pangyo.mapper;

import com.adinstar.pangyo.constant.PangyoEnum;
import com.adinstar.pangyo.model.Comment;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface CommentMapper {
    // TODO: 해당 쿼리 인덱스 잘 타는지 확인하자 (풀스캔 아닌지 확인 필요)
    List<Comment> selectList(@Param("contentType") PangyoEnum.ContentType contentType, @Param("contentId") long contentId, @Param("lastId") long lastId, @Param("size") long size);
    Comment selectById(long id);
    int insert(Comment comment);
    int update(Comment comment);
    int updateStatus(@Param("id") long id, @Param("status") PangyoEnum.CommentStatus status);
}
