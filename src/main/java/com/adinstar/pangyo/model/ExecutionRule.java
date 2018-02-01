package com.adinstar.pangyo.model;

import com.adinstar.pangyo.constant.PangyoEnum.*;
import com.adinstar.pangyo.constant.DateFormat;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ExecutionRule {
    private long id;
    private long turnNum;
    private ExecutionRuleType type;
    private LocalDateTime startDttm;
    private LocalDateTime endDttm;
    private ExecutionRuleStatus status;
    private PangyoLocalDataTime dateTime;


    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = DateFormat.DEFAULT_DATE_FORMAT)
    public LocalDateTime getStartDttm() {
        return startDttm;
    }

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = DateFormat.DEFAULT_DATE_FORMAT)
    public LocalDateTime getEndDttm() {
        return endDttm;
    }
}
