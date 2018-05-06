package com.adinstar.pangyo.mapper;

import com.adinstar.pangyo.constant.PangyoEnum.*;
import com.adinstar.pangyo.model.ExecutionRule;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface ExecutionRuleMapper {
    ExecutionRule selectByTypeAndStatus(@Param("type") ExecutionRuleType type, @Param("status") ExecutionRuleStatus status);
}
