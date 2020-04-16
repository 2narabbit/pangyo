package com.adinstar.pangyo.model;

import com.adinstar.pangyo.constant.PangyoEnum.*;
import lombok.Data;

@Data
public class Comment implements FeedData {
    private long id;
    private ContentType contentType;
    private long contentId;
    private User user;
    private String body;
    private CommentStatus status;
    private PangyoLocalDataTime dateTime;
}
