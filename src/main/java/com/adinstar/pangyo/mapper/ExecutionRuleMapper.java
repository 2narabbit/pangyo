package com.adinstar.pangyo.mapper;

import com.adinstar.pangyo.constant.PangyoEnum.ExecutionRuleStatus;
import com.adinstar.pangyo.constant.PangyoEnum.ExecutionRuleType;
import com.adinstar.pangyo.model.ExecutionRule;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface ExecutionRuleMapper {
    Long selectRunningTurnNum();

    List<ExecutionRule> selectExecutionRuleListByTurnNum(long turnNum);

    List<ExecutionRule> selectExecutionRuleListByStatus(ExecutionRuleStatus status);

    void insertExecutionRule(ExecutionRule executionRule);

    void updateExecutionRuleStatusById(@Param("id") long id, @Param("status") ExecutionRuleStatus status);

    ExecutionRule selectExecutionRuleByTurnAndType(@Param("turnNum") long turnNum, @Param("status") ExecutionRuleType candidate);
}