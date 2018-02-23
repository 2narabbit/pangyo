package com.adinstar.pangyo.model;

import com.adinstar.pangyo.constant.DateFormat;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class PangyoLocalDataTime {
    private LocalDateTime reg;
    private LocalDateTime up;

    // FIXME: 이거 안되는데,,,
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = DateFormat.DEFAULT_DATE_FORMAT)
    public LocalDateTime getReg() {
        return reg;
    }

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = DateFormat.DEFAULT_DATE_FORMAT)
    public LocalDateTime getUp() {
        return up;
    }
}
