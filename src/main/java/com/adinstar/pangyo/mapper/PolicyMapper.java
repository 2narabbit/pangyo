package com.adinstar.pangyo.mapper;

import com.adinstar.pangyo.constant.PangyoEnum.PolicyKey;
import com.adinstar.pangyo.model.Policy;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Repository
@Mapper
public interface PolicyMapper {
    Policy selectPolicyByKey(PolicyKey key);
}
