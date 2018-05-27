package com.adinstar.pangyo.model;

import com.adinstar.pangyo.constant.DateFormat;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class RankData<T> implements FeedData {
    private long id;
    private long dataId;
    private long ranking;
    private LocalDateTime time;
    private T data;
    private PangyoLocalDataTime dataTime;

    @Override
    public long getId(){
        return this.ranking;
    }

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = DateFormat.DEFAULT_DATE_FORMAT)
    public LocalDateTime getTime() {
        return time;
    }
}
