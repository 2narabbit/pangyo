package com.adinstar.pangyo.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class Test {
    @ApiModelProperty(notes="first value")
    private Integer val1;
    private String val2;
    private Boolean val3;
}
