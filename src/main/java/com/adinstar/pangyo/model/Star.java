package com.adinstar.pangyo.model;

import lombok.Data;

@Data
public class Star {
    private long id;
    private String name;
    private String naverOs;
    private String profileImg;
    private String mainImg;
    private boolean display;
    private long fanCount;
    private String message;
    private PangyoLocalDataTime dateTime;
}
