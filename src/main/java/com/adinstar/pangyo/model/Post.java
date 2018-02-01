package com.adinstar.pangyo.model;

import com.adinstar.pangyo.constant.PangyoEnum.*;
import lombok.Data;

@Data
public class Post {
    private long id;
    private long starId;
    private long userId;
    private String body;
    private String img;
    private long viewCount;
    private long likeCount;
    private long commentCount;
    private PostStatus status;
    private PangyoLocalDataTime dateTime;
}
