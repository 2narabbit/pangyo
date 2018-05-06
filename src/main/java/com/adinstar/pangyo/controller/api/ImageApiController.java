package com.adinstar.pangyo.controller.api;

import com.adinstar.pangyo.mapper.ImageMapper;
import com.adinstar.pangyo.model.Image;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.net.URLEncoder;

@RestController
@RequestMapping("/api/image")
public class ImageApiController {
    @Autowired
    private ImageMapper imageMapper;

    @RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = MediaType.IMAGE_JPEG_VALUE)
    public byte[] getList(@PathVariable("id") String id) throws Exception {
        Image image = imageMapper.selectById(URLEncoder.encode(id, "UTF-8"));
        return image.getData();
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.POST)
    public void upload(@PathVariable("id") String id, @RequestBody byte[] data) throws Exception {
        Image image = new Image();
        image.setId(URLEncoder.encode(id, "UTF-8"));
        image.setContentType(MediaType.IMAGE_JPEG_VALUE);
        image.setData(data);

        imageMapper.insert(image);
    }
}
