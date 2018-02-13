package com.adinstar.pangyo.helper;

public class PageHelper {
    private int page;
    private int size;

    public PageHelper(int page, int size) {
        this.page = page;
        this.size = size;
    }

    public int getStartOffset() {
        return (this.page - 1) * this.size;
    }

    public int getEndOffset() {
        return this.page * this.size;
    }

}