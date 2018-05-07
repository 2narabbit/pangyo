package com.adinstar.pangyo.mapper;

import com.adinstar.pangyo.constant.PangyoEnum;
import com.adinstar.pangyo.model.ActionHistory;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface ActionHistoryMapper {
    ActionHistory selectByActionTypeAndContentTypeAndContentIdAndUserId(@Param("actionType") PangyoEnum.ActionType actionType, @Param("contentType") PangyoEnum.ContentType contentType, @Param("contentId") long contentId, @Param("userId") long userId);
    // TODO : 아래 쿼리 풀스캔 안일어나는지 보기
    List<ActionHistory> selectListByActionTypeAndContentTypeAndContentIdsAndUserId(@Param("actionType") PangyoEnum.ActionType actionType, @Param("contentType") PangyoEnum.ContentType contentType, @Param("contentIds") List<Long> contentIds, @Param("userId") long userId);
    int insert(ActionHistory actionHistory);
    int deleteByActionTypeAndContentTypeAndContentIdAndUserId(@Param("actionType") PangyoEnum.ActionType actionType, @Param("contentType") PangyoEnum.ContentType contentType, @Param("contentId") long contentId, @Param("userId") long userId);
}
