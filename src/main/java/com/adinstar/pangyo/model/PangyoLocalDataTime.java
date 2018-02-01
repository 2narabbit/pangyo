package com.adinstar.pangyo.model;

import com.adinstar.pangyo.constant.DateFormat;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;

public class PangyoLocalDataTime {
    private LocalDateTime reg;
    private LocalDateTime up;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = DateFormat.DEFAULT_DATE_FORMAT)
    public LocalDateTime getReg() {
    return reg;
}

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = DateFormat.DEFAULT_DATE_FORMAT)
    public LocalDateTime getUp() {
        return up;
    }
}
