package com.adinstar.pangyo.model;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class Policy {
    private int id;
    private String key;
    private String value;
    private String description;
    private LocalDateTime reg;
    private LocalDateTime end;
}
