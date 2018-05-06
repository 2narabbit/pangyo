package com.adinstar.pangyo.service;

import com.adinstar.pangyo.mapper.ImageMapper;
import com.adinstar.pangyo.model.Image;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ImageService {
    @Autowired
    private ImageMapper imageMapper;

    public Image get(String id) {
        return imageMapper.selectById(id);
    }

    public void add(Image image) {
        imageMapper.insert(image);
    }
}
