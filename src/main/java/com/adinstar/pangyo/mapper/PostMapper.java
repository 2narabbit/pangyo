package com.adinstar.pangyo.mapper;

import com.adinstar.pangyo.model.Post;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface PostMapper {
    List<Post> selectList(@Param("lastId") long lastId, @Param("size") int size);
    List<Post> selectListByStarId(@Param("starId") long starId, @Param("lastId") long lastId, @Param("size") int size);
    Post selectById(long id);
    int insert(Post post);
    int update(Post post);
    int increaseViewCount(long id);
    int delete(long postId);
}
