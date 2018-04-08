package com.adinstar.pangyo.model;

import lombok.Data;

@Data
public class Policy {
//    private long commentLov;
//    private long postLov;
//    private long likeLov;
//    private int candidateRange;
//    private int candidateDoneDate;
//    private int campaignRange;
//    private int campaignDoneDate;

    private int id;
    private String key;
    private String value;
    private PangyoLocalDataTime dataTime;
}
