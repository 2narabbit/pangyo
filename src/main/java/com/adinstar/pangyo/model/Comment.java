package com.adinstar.pangyo.model;

import com.adinstar.pangyo.constant.PangyoEnum.*;
import lombok.Data;

@Data
public class Comment {
    private long id;
    private ContentType contentType;
    private long contentId;
    private long userId;
    private String body;
    private CommentStatus status;
    private PangyoLocalDataTime dataTime;
}
