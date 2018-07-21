package com.adinstar.pangyo.model;

import lombok.Data;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Data
public class FeedResponse<T extends FeedData> {
    private boolean hasMore;
    private long lastId;
    private List<T> list;

    public FeedResponse(List<T> list, int expactListSize) {
        if (list == null || list.isEmpty()) {
            this.list = new ArrayList<>();
        } else {
            if (list.size() > expactListSize) {
                this.list = list.subList(0, expactListSize);
                this.hasMore = true;
            } else {
                this.list = list;
                this.hasMore = false;
            }

            this.lastId = this.list.get(this.list.size() - 1).getId();
        }
    }

    public static final FeedResponse<?> EMPTY_LIST  = new FeedResponse(Collections.emptyList(), 0);
}
