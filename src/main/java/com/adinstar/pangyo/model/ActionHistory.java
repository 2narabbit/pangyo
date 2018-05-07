package com.adinstar.pangyo.model;

import com.adinstar.pangyo.constant.PangyoEnum;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ActionHistory {
    private long id;
    private PangyoEnum.ActionType actionType;
    private PangyoEnum.ContentType contentType;
    private long contentId;
    private long userId;
    private PangyoLocalDataTime dateTime;
}
