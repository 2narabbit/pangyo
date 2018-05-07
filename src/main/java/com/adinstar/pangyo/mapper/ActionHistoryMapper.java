package com.adinstar.pangyo.mapper;

import com.adinstar.pangyo.constant.PangyoEnum;
import com.adinstar.pangyo.model.ActionHistory;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface ActionHistoryMapper {
    ActionHistory selectByActionTypeAndContentTypeAndContentIdAndUserId(@Param("actionType") PangyoEnum.ActionType actionType, @Param("contentType") PangyoEnum.ContentType contentType, @Param("contentId") long contentId, @Param("userId") long userId);
    int insert(ActionHistory actionHistory);
    int deleteByActionTypeAndContentTypeAndContentIdAndUserId(@Param("actionType") PangyoEnum.ActionType actionType, @Param("contentType") PangyoEnum.ContentType contentType, @Param("contentId") long contentId, @Param("userId") long userId);
}
