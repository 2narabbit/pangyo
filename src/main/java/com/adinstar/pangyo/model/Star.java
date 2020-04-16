package com.adinstar.pangyo.model;

import lombok.Data;

@Data
public class Star {
    private long id;
    private String name;
    private String job;
    private String naverOs;
    private String profileImg;
    private String mainImg;
    private Boolean display;
    private long fanCount;
    private String message;
    private PangyoLocalDataTime dateTime;
}
