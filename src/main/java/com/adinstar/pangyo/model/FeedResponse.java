package com.adinstar.pangyo.model;

import lombok.Data;

import java.util.List;

@Data
public class FeedResponse<T> {
    private boolean hasMore;
    private long lastId;
    private List<T> list;
}
