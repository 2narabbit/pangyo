package com.adinstar.pangyo.mapper;

import com.adinstar.pangyo.constant.PangyoEnum;
import com.adinstar.pangyo.model.Post;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface PostMapper {
    List<Post> selectListByStarId(@Param("starId") long starId, @Param("lastId") long lastId, @Param("size") int size);
    Post selectById(@Param("id") long id);
    int insert(Post post);
    int update(Post post);
    int updateLikeCount(@Param("id") long id, @Param("delta") int delta);
    int updateViewCount(@Param("id") long id, @Param("delta") int delta);
    int updateCommentCount(@Param("id") long id, @Param("delta") int delta);
    int updateStatus(@Param("id") long id, @Param("status") PangyoEnum.PostStatus status);
}
