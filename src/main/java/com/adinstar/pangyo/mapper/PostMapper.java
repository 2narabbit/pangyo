package com.adinstar.pangyo.mapper;

import com.adinstar.pangyo.constant.PangyoEnum;
import com.adinstar.pangyo.model.Post;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface PostMapper {
    List<Post> selectListByStarId(@Param("starId") long starId, @Param("lastId") long lastId, @Param("size") int size);
    Post selectByStarIdAndId(@Param("starId") long starId, @Param("id") long id);
    int insert(Post post);
    int update(Post post);
    int updateViewCount(@Param("id") long id, @Param("delta") int delta);
    int updateCommentCount(@Param("id") long id, @Param("delta") int delta);
    int updateStatus(@Param("starId") long starId, @Param("id") long id, @Param("status") PangyoEnum.PostStatus status);
}
