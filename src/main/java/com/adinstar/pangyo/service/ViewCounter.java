package com.adinstar.pangyo.service;

import org.springframework.stereotype.Service;

import java.util.function.BiFunction;

@Service
public class ViewCounter {

    public void updateViewCount(Long id, Integer delta, BiFunction<Long, Integer, Integer> function) {
        try {
            function.apply(id, delta);
        } catch (Exception e) {}
    }
}
