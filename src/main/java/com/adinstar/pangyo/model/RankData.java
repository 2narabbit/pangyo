package com.adinstar.pangyo.model;

import com.adinstar.pangyo.constant.DateFormat;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class RankData<T> implements FeedData {
    private long ranking;
    private LocalDateTime updateTime;
    private T content;

    public RankData() {}

    public RankData(long ranking, T content, LocalDateTime updateTime) {
        this.ranking = ranking;
        this.content = content;
        this.updateTime = updateTime;
    }

    @Override
    public long getId(){
        return this.ranking;
    }

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = DateFormat.DEFAULT_DATE_FORMAT)
    public LocalDateTime getUpdateTime() {
        return updateTime;
    }
}
