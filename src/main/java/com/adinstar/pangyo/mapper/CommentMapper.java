package com.adinstar.pangyo.mapper;

import com.adinstar.pangyo.model.Comment;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface CommentMapper {
    // TODO: 해당 쿼리 인덱스 잘 타는지 확인하자 (풀스캔 아닌지 확인 필요)
    List<Comment> selectList(@Param("contentType") String contentType, @Param("contentId") long contentId, @Param("lastId") long lastId, @Param("size") long size);
}
