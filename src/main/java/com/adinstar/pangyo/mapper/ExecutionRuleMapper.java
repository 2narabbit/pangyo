package com.adinstar.pangyo.mapper;

import com.adinstar.pangyo.constant.PangyoEnum.*;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface ExecutionRuleMapper {
    long selectRuleId(@Param("type") ExecutionRuleType type, @Param("status") ExecutionRuleStatus status);
}
