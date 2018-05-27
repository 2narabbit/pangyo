package com.adinstar.pangyo.mapper;

import com.adinstar.pangyo.constant.PangyoEnum.PolicyKey;
import com.adinstar.pangyo.model.Policy;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface PolicyMapper {
    Policy selectPolicyByKey(PolicyKey key);
}
