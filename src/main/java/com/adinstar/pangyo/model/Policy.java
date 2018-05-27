package com.adinstar.pangyo.model;

import lombok.Data;

@Data
public class Policy {
    private int id;
    private String key;
    private String value;
    private String description;
    private PangyoLocalDataTime dataTime;
}
