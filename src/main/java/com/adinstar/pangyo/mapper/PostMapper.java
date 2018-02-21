package com.adinstar.pangyo.mapper;

import com.adinstar.pangyo.model.Post;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface PostMapper {
    List<Post> selectList(@Param("startOffset") int startOffset, @Param("endOffset") int endOffset);
    Post selectById(int id);
    int insert(Post post);
    int update(Post post);
    int delete(Integer postId);
}
