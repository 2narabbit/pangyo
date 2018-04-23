package com.adinstar.pangyo.model;

import lombok.Data;

@Data
public class Image {
    private String id;
    private String contentType;
    private int contentLength;
    private byte[] data;
}
